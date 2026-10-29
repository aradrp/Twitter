# Twitter coursework demo

A local Java Swing client and Java socket server built as a university project. This repository preserves the original project structure while fixing the main sign-up, sign-in, posting, timeline, profile, like, and save flows.

## Run locally

Requires **JDK 21** on `PATH` and PowerShell. Start the server in one terminal:

```powershell
.\run-server.ps1
```

Then start the client in a second terminal:

```powershell
.\run-client.ps1
```

The server listens only on `127.0.0.1:12345`. Start it before the client. Account data is written to `Server/SAVES/`, which Git ignores. Use test accounts; this is an educational local application, not an internet service.

## What works

- Register and sign in with local accounts. Passwords are stored as salted PBKDF2 hashes, rather than serialized with user profiles.
- Publish posts of 1 to 280 characters, mark them private, and optionally allow other users to save them.
- View public posts in For You, view your own posts in Profile, and like or save posts. These actions persist locally.
- Open multiple clients against the same server.

## Current limits

- Search, notifications, settings, and following are unfinished and disabled in the client.
- Data uses Java serialization and local files. It is not suited to untrusted network users or deployment to the public internet.
- The timeline sorts by time of day because the original post model has no full creation timestamp.
- The UI is a fixed-width desktop Swing layout. A 600 px wide display and a recent desktop Java runtime are recommended.

## Smoke test

With the server running, compile and run the protocol test from another PowerShell terminal:

```powershell
.\test.ps1
```

The test creates two local accounts in `Server/SAVES/`, publishes public and private posts, and checks login, the timeline, likes, saves, and private-post visibility. The test data stays out of Git.

## Layout

- `Client/src`: Swing UI and local socket client
- `Client/pics`: image assets used by the UI
- `Server/src`: socket server, persistence, and credentials
- `tests`: protocol smoke test

The original university project is kept separately from this curated repository.
