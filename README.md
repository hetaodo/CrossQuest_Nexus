CrossQuest: Nexus

**CrossQuest: Nexus** is an innovative cross-APK interactive game that allows players to transfer game progress across multiple independent games. Players transfer data and complete tasks between different games, such as opening a door in Game A and collecting keys to unlock it in Game B.

## Project Overview

The core concept of this project is to create a new multi-game interactive experience by using broadcast mechanisms to communicate progress between different games. Each game (e.g., Game A, Game B) runs as a separate APK, but they can only transfer information and progress between them within the same system.

### Key Features:
- **Multiple Games**: Through broadcast data, game branches can share progress (e.g., quest status, item collection, etc.).
- **Game Installation Guide**: A single Android app guides users to install and launch all game APKs.
- **Cross-APK Data Transfer**: Using Android's broadcast mechanism, data can be exchanged between Game A and Game B.
- **Open Source Project**: All code is publicly available, and contributions and participation are welcome.

## Technology Stack

- **Unity**: For developing game branches.
- **Android Studio**: For developing the game installation guide app and cross-APK communication.
- **Java**: For developing the native Android components.
- **BroadcastReceiver**: For communication and data transfer between games.
- **SharedPreferences/SQLite**: For storing game progress data.

## Directory Structure

- `/game-a`: Unity project for Game A.
- `/game-b`: Unity project for Game B.
- `/game-install-guide`: Android app for guiding game installation.
- `/shared-lib`: Shared game progress management library and utility classes.
- `/assets`: Common game resource files (such as images, sound effects, etc.).

## How to Get Started

### Installation and Running
1. Clone the project:
    ```bash
    git clone https://github.com/hetaodo/CrossQuest_Nexus.gitq