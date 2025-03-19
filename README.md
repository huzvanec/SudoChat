# SudoChat

A simple plugin that allows you to send messages and execute commands as other players.  
No config. Works out of the box.

## Supported Platforms

✅ Paper-based Minecraft servers **1.21.4+**  
❌ Spigot and Bukkit are NOT supported

## Permissions

To use `/sudo`, you must have the `sudochat.sudo` permission.

- You can grant this permission using a plugin like [LuckPerms](https://luckperms.net/).
- Operators (`/op`) automatically have access.

## Usage

The command format:

```
/sudo <targets> <message>
```

- `<targets>` -> A player name or a different [target selector](https://minecraft.wiki/w/Target_selectors).
- `<message>` -> A chat message (text) or a command prefixed with `/`.

> [!NOTE]
> Note that only users with the `minecraft.command.selector` permission will be able to use target selectors.

## Examples

### Sending a message as another player

```
/sudo Steve Hello! I just joined the server.
/sudo @a[name=!Steve] Welcome!
```

### Executing a command as another player

```
/sudo Steve /gamemode creative
/sudo @a[name=!Steve] /help
```

## Building

Requirements

- [Git](https://git-scm.com/downloads)
- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)

```bash
git clone https://github.com/huzvanec/SudoChat.git
cd SudoChat
./gradlew shadowJar
```

After building, the output JAR will be located in `./build/libs/`.