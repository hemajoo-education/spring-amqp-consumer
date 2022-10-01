# Hemajoo Education - Spring RabbitMQ

The purpose of this educational project is to demonstrate the ease of use of the **RabbitMQ** message broker with **Spring** framework.

## Introduction
<hr>

This project aims to handle communication between an event service and players of a **MMORPG** game.<br>

In our example, we have limited the events to 4 different types:

- Battleground
- Arena
- Raid
- Dungeon

## Queues
<hr>

A message broker (**RabbitMQ**) is used to exchange messages between the different actors of the system. This enforces the decoupling of the components and ensure 
message exchange is realized asynchronously.

When exchanging messages from a player to an event service, the following queues are involved:

### 🗂 Player queues

| Queue Name                                  | Queue Description                                                     |
|:--------------------------------------------|:----------------------------------------------------------------------|
| `wow.player.[PLAYER_ID].event.battleground` | Contains messages relating to battleground events for a given player. |
| `wow.player.[PLAYER_ID].event.arena`        | Contains messages relating to arena events for a given player.        |
| `wow.player.[PLAYER_ID].event.raid`         | Contains messages relating to raid events for a given player.         |
| `wow.player.[PLAYER_ID].event.dungeon`      | Contains messages relating to dungeon events for a given player.      |
| `wow.player.[PLAYER_ID].event.dungeon`      | Contains messages relating to dungeon events for a given player.      |

To correctly route the message, the `wow.player.header-exchange` exchange is used and an associated header composed of the following mandatory properties:

| Property Name | Description                                  |
|:--------------|:---------------------------------------------|
| `playerId`    | Player identifier.                           |
| `eventType`   | `ARENA`, `BATTLEGROUND`, `RAID` or `DUNGEON` |

### 🗂 Event Service queues

### Generic Battleground service

This service is a generic service responsible to manage communication with players willing to participate in battleground events.

| Queue Name          | Queue Description                                                               |
|:--------------------|:--------------------------------------------------------------------------------|
| `wow.service.event` | Queue of messages relating to battleground events sent by the players.          |

to correctly route the message, the `wow.service.event.direct-exchange` and the routing key `SERVICE_EVENT`.

### Instance Battleground service

This service is an instance service responsible to manage communication with players participating in a specific _instantiated_ battleground.

| Queue Name                                  | Queue Description                                                               |
|:--------------------------------------------|:--------------------------------------------------------------------------------|
| `wow.service.event.[EVENT_TYPE].[EVENT_ID]` | Queue of messages relating to a specific battleground instance sent by players. |

to correctly route the message, the `wow.event.instance.header-exchange` exchange is used and an associated header composed of the following mandatory properties:

| Property Name | Description                                            |
|:--------------|:-------------------------------------------------------|
| `eventType`   | `ARENA`, `BATTLEGROUND`, `RAID` or `DUNGEON`           |
| `eventId`     | Representing the identifier of the instantiated event. |

**Note:**<br>
Here, we could adopt two different strategies to manage these queues for events' instances:

- First one is to pre-create the necessary queues and binding so that we know the maximum number of possible instantiated events of a given type. If all queues are used, then before being able to start a new event, we need to wait one ends.
- The second approach is to have a dynamic setup and binding of the queues.


# Event messages

<hr>

## Event Registration messages

<hr>

### ✉️ Event Registration Request message

This message is sent by a player to request to participate in a battleground event. It is sent to the `wow.event.service.battleground` queue
using the `wow.battleground.direct-exchange` exchange and the `battleground` routing key.

|     Property | Value                              |
|-------------:|:-----------------------------------|
|       Sender | `Player Entity`                    |
|       Target | `Event Service`                    |
| Target Queue | `wow.battleground.registration`    |
|     Exchange | `wow.battleground.direct-exchange` |
|  Routing key | `battleground`                     |

### 📦 Event Registration Request message structure

|        Message Property | Description                          |
|------------------------:|:-------------------------------------|
|           `messageType` | `MESSAGE_EVENT_REGISTRATION_REQUEST` |
|               `eventId` | Identifier of the event.             |
|       `playerReference` | Player reference.                    |
|       `avatarReference` | Avatar reference.                    |

This event registration request will then be processed by a dedicated microservice and a response will be generated 
which will be sent to the player's notification queue the avatar belongs to.

**Note:**<br>
We could also have used a **header** queue type to route messages based on the content of a header. In such a case, the header should contain the following parameters:

|      Property | Description    |
|--------------:|:---------------|
|  `entityType` | `PLAYER`       |
|    `entityId` | `123456789`    |
|   `queueType` | `NOTIFICATION` |
|   `eventType` | `BATTLEGROUND` |

<hr>

### ✉️ Event Registration Response message

|     Property | Value                                     |
|-------------:|:------------------------------------------|
|       Sender | `Event Service`                           |
|       Target | `Player`                                  |
| Target queue | `wow.player.123456789.event.notification` |
|     Exchange | `wow.player.header-exchange`              |
|  Routing key | `N/A`                                     |

And the header composed of:

| Header Property | Value          |
|----------------:|:---------------|
|    `entityType` | `PLAYER`       |
|      `entityId` | `123456789`    |
|     `queueType` | `NOTIFICATION` |

This message is sent by a backend event registration microservice to a player having posted a prior event registration message.
The response message can be of two types (`ACCEPTED` or `REJECTED`) depending on some conditions that are beyond the scope of this example.

### 📦 Event Registration Response message structure

|        Property | Description                                                                                  |
|----------------:|:---------------------------------------------------------------------------------------------|
|   `messageType` | `MESSAGE_EVENT_REGISTRATION_RESPONSE`                                                        |
|  `responseType` | Can be: `ACCEPTED` or `REJECTED`.                                                            |
|    `reasonType` | Rejection reason identifier in case the response is `REJECTED`, null otherwise.              |
|       `eventId` | Event identifier (in our example a battleground identifier) the player wants to participate. |
| `estimatedTime` | The estimated time to wait until the event is expected to start.                             |

<hr>

### ✉️ Event Notification messages

This type of message can be sent by players to an event service or by event services to players.

### 📦 Event Notification (registration cancellation) message structure

|        Queue | Value                                  |
|-------------:|:---------------------------------------|
|       Sender | `Player`                               |
|       Target | `Event Service`                        |
| Target Queue | `wow.battleground.registration`        |
|     Exchange | `wow.battleground.direct-exchange`     |
|  Routing key | `battleground`                         |

|      Message Property | Description                  |
|----------------------:|:-----------------------------|
| `messageCategoryType` | `MESSAGE_EVENT_NOTIFICATION` |
|         `messageType` | `REGISTRATION_CANCELLATION`  |
|     `playerReference` | Player reference.            |
|             `eventId` | Event identifier.            |

### 📦 Event Notification (estimated time changed) message structure

|        Queue | Value                                     |
|-------------:|:------------------------------------------|
|       Sender | `Event Service`                           |
|       Target | `Player`                                  |
| Target queue | `wow.player.123456789.event.notification` |
|     Exchange | `wow.player.header-exchange`              |
|  Routing key | `N/A`                                     |

|      Message Property | Description                                                                       |
|----------------------:|:----------------------------------------------------------------------------------|
| `messageCategoryType` | `MESSAGE_EVENT_NOTIFICATION`                                                      |
|         `messageType` | `ESTIMATED_TIME_CHANGED`                                                          |
|             `eventId` | Identifier of the event.                                                          |
|       `estimatedTime` | Estimated time left (expressed in seconds) before the event is expected to start. |

### 📦 Event Notification (event cancelled) message structure

#### Queue information:

|              | Value                                                   |
|-------------:|:--------------------------------------------------------|
|       Sender | `SERVICE_EVENT`                                         |
|       Target | `PLAYER`                                                |
| Target queue | `wow.player.[PLAYERID].event.battleground.notification` |
|     Exchange | `wow.player.header-exchange`                            |
|  Routing key | `N/A`                                                   |

#### Message Header information:

| Header Property Name | Header Property Value                      |
|---------------------:|:-------------------------------------------|
|         `entityType` | `PLAYER`                                   |
|           `entityId` | `123456789`                                |
|          `queueType` | `NOTIFICATION`                             |
|          `eventType` | `BATTLEGROUND`                             |

#### Message Content information:

|      Message Property | Description                              |
|----------------------:|:-----------------------------------------|
| `messageCategoryType` | `MESSAGE_EVENT_NOTIFICATION`             |
|         `messageType` | `EVENT_CANCELLED`                        |
|             `eventId` | Identifier of the event.                 |
|          `reasonType` | Reason why the event has been cancelled. |

<hr>

### ✉️ Message: MESSAGE_EVENT_NOTIFICATION_CANCELLED

This notification message is sent by a microservice to a player's queue to notify an event the player was previously registered has been cancelled. 

| Property      | Description                                     |
|:--------------|:------------------------------------------------|
| `messageType` | Must be: `MESSAGE_EVENT_NOTIFICATION_CANCELLED` |
| `reasonType`  | Reason why the event has been cancelled.        |
