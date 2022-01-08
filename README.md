<h1 align="center">AllowDB</h1>
<h3 align="center">MySQL-powered allowlist Spigot/Paper plugin</h3>
<p align="center">
  <a href="https://github.com/alteamc/allowdb/actions/workflows/test_on_pr.yml"><img alt="Workflow status" src="https://img.shields.io/github/workflow/status/alteamc/minequery/Go/master"></a>
  <a href="https://app.codacy.com/gh/alteamc/allowdb"><img alt="Codacy grade" src="https://img.shields.io/codacy/grade/1e7879f7f9c84e9bbd384d2cf045c2b0"></a>
  <a href="https://github.com/alteamc/allowdb"><img alt="Java version" src="https://img.shields.io/badge/java-17-critical"></a>
  <a href="https://github.com/alteamc/allowdb/releases/latest"><img alt="Latest release" src="https://img.shields.io/github/v/release/alteamc/allowdb"></a>
  <a href="https://github.com/alteamc/allowdb/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/alteamc/allowdb"></a>
  <a href="https://discord.gg/9ruheUG3Wg"><img alt="Discord chat" src="https://img.shields.io/discord/929337829610369095"></a>
</p>

## Basic usage

### Installation

1. Download the most recent release from [releases page](https://github.com/alteamc/allowdb/releases).
2. Move downloaded JAR to `plugins/` folder.
3. Restart (or reload) your server.
4. Configure MySQL connection in `plugins/AllowDB/config.yml` config file.
5. Restart (or reload) your server again, or restart AllowDB plugin if you have any plugin management utilities
   installed.

### Configuring plugin

### Locale

Currently AllowDB ships with two locales bundled: English (`en`) and Russian (`en`.)

For creating/editing locales see [creating/editing locales](#creatingediting-locales) section.

### MySQL connection URL

MySQL connection URL has the following format:

```
jdbc:mysql://user:password@127.0.0.1:3306/allows
```

In this reference connection string:

* `jdbc:mysql://` prefix is a mandatory part of URL
* `user` is a sample username of database user
* `password` is a sample password of database user
* `127.0.0.1` is IP address to MySQL database host
* `3306` is default MySQL port
* `allows` is name of MySQL database to use

**Note!** If username or password contain special characters (out of alphanumeric character set, e.g. `!@#$%^&*()`), you
will need to URL-encode its value.

### Kick message

You can configure server kick message (that unallowed users will see when they will try to connect) with
`messages.not-listed` parameter. Color codes are also supported with `§` (section sign.)

### Managing allowlist

AllowDB lets you manage your allowlist with `/adb add <nickname or UUID>` or `/adb remove <nickname or UUID>` commands
with username or UUID (with or without dashes).

### Reloading configuration

AllowDB provides `/adb reload` command for reloading configuration from disk and reconnecting to database.

### Configuring permissions

AllowDB has clear and concise permission nodes for every command as well as wildcard permissions for full access to
every command (`allowdb.command.*` and `allowdb.*`.)

However, you will still need `allowdb.command` permission (note that it *does not* end with `.*`) for players to use any
of `/adb` commands (and for it to appear in command list when you type it.)

| Command       | Permission node          |
|---------------|--------------------------|
| `/adb add`    | `allowdb.command.add`    |
| `/adb remove` | `allowdb.command.remove` |
| `/adb reload` | `allowdb.command.reload` |
| `/adb help`   | `allowdb.command.help`   |

## Advanced usage

### Creating/editing locales

Locales are stored in files `messages_LANG.yml` files in JAR file root (where `LANG` is two-letter locale code,
e.g. `en` or `ru`.) If you want to edit existing locales or add your own, edit one of these files or copy one of them
for reference and edit it for your own needs.

Localized strings use standard `String.format(...)` formatting placeholders (e.g. `%1$s`, `%2$s` where digit is
positional placeholder index.)