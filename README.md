# RainBowCreation core jar for network
RainBowCreation by [NathanielSong, VocanicZ]

## rainbowcreation.net

!this plugin supports all ``bukkit``, ``spigot``, ``paper``, ``multipaper`` and ``folia`` with minecraft version ``1.8+``!
Our goal is all in one network of plug-in that you can plug and play with no additional plugin required!
RainBowCreation Network building concept
- no pay-to-win at any point!
- mod-like experience with 0 mods needed or serverside only!
- 0 commands need no more /lobby /home
- Gui base system
- Cluster-based minecraft server compatibility, we develop an easy scalable server
- 1 world map to all server networks, everything synchronized!
- automatic node growth (every city develops itself depending on player activity)
- realistic market price system
- cross-server item/charter stat

additional projects from this network
- ``RainBowLite``
  a clone of RainBowCreation but build on single-server based with flexible configuration
  plugin passed on spigot NMS codebase
  compatibility with Essentials, Luckperms, Vault, Dynmap
  (player play on this server still able to join to/from RainBowCreation mainnet without command or disconnect to Minecraft main menu)
- ``RainBowCreation-ClientExtension``
  Utility mod for better experiencing our network!
  (won't work on RainBowLite)
- ``RainBowBot``
  discord server that moves you to every room automatically depends on your location on the map/lobby
  (like RainBowTon but on discord with no 3D proximity sound)

### build requirements
- gradle 8.11
- java 21, 17, 8 installed
### build
```./gradlew shadowJar```

### server requirement
- ``bukkit``/``spigot``/``paper``/``multipaper``/``folia`` version ``1.8+`` please use ``bootstrap``jar
- ``multipaper-master``/``bungeecord``/``velocity`` use ``proxies``jar
- third party plugin or want to communicate with ``core``/``proxies`` please use ``api`` to dependencies

since 2020.