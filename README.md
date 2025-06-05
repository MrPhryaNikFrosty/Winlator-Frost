<p align="center">
	<img src="logo.png" width="376" height="128" alt="Winlator-Frost Logo" />  
</p>
<a href="https://git.io/typing-svg"><img src="https://readme-typing-svg.demolab.com?font=Fira+Code&pause=1000&width=500&lines=Welcome+to+Winlator%40Frost+Official+Github!;If+you+like+the+mod%2C+please+star+the+repo!;Established+since+2023,+No.1+Winlator+mod;Modded+with+%E2%9D%A4%EF%B8%8F+by+PhryaNik+from+Thailand" alt="Typing SVG" /></a>

# Winlator@Frost
<p align="left">
  <img src="https://img.shields.io/github/downloads/MrPhryaNikFrosty/Winlator-Frost/total" alt="Total Downloads" width="150">
</p>

Winlator@Frost is an Android application that lets you to run Windows (x86_64) applications with Wine and Box86/Box64. Originally modified from [Brunodev85](https://github.com/brunodev85/Winlator)

Subscribe My YouTube Channel [Click Here](https://youtube.com/@emuzone20?si=FUsBJ7ZmLY7Ktu1y)​

Join us in our official Discord server [Click Here](https://discord.gg/Q74CNHJnq2)​

# Winlator@Frost Installation

1. Download and install the APK (Winlator@Frost 10.0Final V4.1.apk) from [GitHub Releases](https://github.com/MrPhryaNikFrosty/Winlator-Frost/releases)
2. Launch the app and wait for the installation process to finish
3. Now you can enjoy your favorite pc games! But please make sure that you select the renderer based on your phone chips, you can read the system requirements below to learn more
4. You can also try out our new winlator mod glibc (for now 7.1.3) from the releases
5. For older or legacy version of Winlator@Frost which are not available in Github releases can get it from [here](https://mega.nz/folder/dQtDzI7a#47pl7EQ8AWwY2jV-665ikQ)
6. If you like the mod, please star the repo 😁

# Video testing of Winlator@Frost by me
----

- Test on Honor X9b (SD 6 GEN 1)
[![Play on Youtube](https://i.ytimg.com/vi/yT8HeDDXYNk/hqdefault.jpg?sqp=-oaymwEnCNACELwBSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLBg6mwWOnCdGRZ_j0fr4rOyc_4oyQ)](https://youtu.be/yT8HeDDXYNk?si=WqEvuNUsxdjElomr)

- Test on Poco F3 (SD 870)
[![Play on Youtube](https://i.ytimg.com/vi/lWRubu4LITg/hqdefault.jpg?sqp=-oaymwFBCNACELwBSFryq4qpAzMIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB8AEB-AH-CYAC0AWKAgwIABABGC4gaChyMA8=&rs=AOn4CLCiWNAFDSSx8jICLbdoVhfUGxnDoA)](https://youtu.be/lWRubu4LITg?si=u0v8kX7HiKpdeo-1)

- Test on Honor Magic 5 (SD 8 GEN 2)
[![Play on Youtube](https://i.ytimg.com/vi/lRmnb_3untM/hqdefault.jpg?sqp=-oaymwEnCNACELwBSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLD2qDb4xNO8AYoV0yAqvZoG2o-PkQ)](https://youtu.be/lRmnb_3untM?si=8bMl1BxodFHgOAnm)

- Test on Poco X5 (SD 695)
[![Play on Youtube](https://i.ytimg.com/vi_webp/yQl-OPTIb3E/hqdefault.webp)](https://youtu.be/yQl-OPTIb3E?si=QcSX-oz1CfWwVmXb)

- For more video tests, please head to my YouTube channel, link to my channel are in above of this repository

----

# Useful Tips & Features of Winlator@Frost

- If you are experiencing performance issues, try changing the preset for Box86/Box64 in Container Settings -> Advanced Tab.
- For applications that use .NET Framework, try installing Wine Mono found in Start Menu -> System Tools.
- If some older games don't open, try adding the environment variable MESA_EXTENSION_MAX_YEAR=2003 in Container Settings -> Environment Variables.
- Try running the games using the shortcut on the Winlator home screen, there you can define individual settings for each game.
- If you want to hide the annoying dxvk hud, please untick `devinfo`, `frametimes` and `gpuload` in the environment variables inside the container settings. Or you can also delete the env `DXVK_HUD` but not recommended
- If you want to use VirGL in winlator version (7.1 and newer), make sure you change the env of `MESA_GL_VERSION_OVERRIDE`from `3.3COMPAT`to `2.1` or `3.1COMPAT`
For other version of winlator you can run it using `3.3COMPAT` or `4.0`
- Enabled the `BOX64_DYNAREC_WEAKBARRIER` can improve the performance for 64 bit games
- To display low resolution games correctly, try to enabling the `Force Fullscreen` option in the shortcut settings
- To speed up the installers, try changing the Box64 preset to `Intermediate` in Container Settings -> Advanced Tab
- You can now enable `MANGOHUD` for glibc version, not yet for proot and winlator 10 version
- For some unity games especially to fix unity games from crashing, try use stability preset and add `-force-gfx-direct -​force-d3d11-singlethread` in the exec arguments inside the shortcut of the unity games
- For some unity games, the games might not be able to launch in proot, so please try in glibc

#### To show the VKD3D fps hud, you can try to add this env into environment variables:
    VK_INSTANCE_LAYERS=VK_LAYER_MESA_overlay

- Please take note that the before `=` are name and after it are value

# System requirement for Winlator@Frost
## Device
- Android: 8.0 or newer
- RAM: 3 ~ 4 GB are minimum requirements but 6 GB are recommended
- ROM: Any size of ROM are supported and SD Card are also supported but some type of SD Card doesn't recognize as drive in winlator
- Architecture: at least arm64-v8a are required

## Graphics Driver for device GPU
- `Turnip` - Only for Snapdragon that has `Adreno 6xx and 7xx` only at the moment. `Adreno 8xx (8 elite, 8s gen 4, 7s gen 3, 6 gen 4) and old Adreno 5xx or below` are not working. You have to use VirGL or Vortek if you're using this type of GPU.
- `Vortek` - Universal, all GPUs will work especially for Adreno 8xx which doesn't support turnip can use this driver. This driver are also work in Mali but not all Mali are works
- `VirGL` - Universal, all GPUs `like Mali, PowerVR, Xclipse, etc and old Adreno 5xx and below or Adreno 8xx` might work with VirGL but only Ddraw, DX8 or DX9 games and OpenGL 3.1 below games are working
- `LLVMPIPE` - Software renderer, not recommended for games. Use this if you want to run software apps, this feature are only available in winlator 7.1 releases or older

# Known issue in Winlator@Frost
- ~~Snapdragon 8s gen 3 `Adreno​ 735` container not starting issue~~ (now it's working)
- ~~Some game in Snapdragon 8 gen 3 may not working or have a performance hit~~ (try change turnip version)
- ~~Some game have a xinput issue which virtual gamepad are not working~~ (solved by using 8.0)
- ~~Odin 2 users, external gamepad or even virtual gamepad will facing the xinput issue (like joystick issue) in glibc version~~ (try change preferred input api to xinput)
- ~~VirGL in glibc version doesn't work at all~~ (it's working by using the winlator glibc with VirGL version)
- SD card doesn't read properly in newer versions of winlator
- USB driver with OTG doesn't recognise as drives in winlator

# Information
This project has been in constant development since version 1.0, the current app source code is up to version 7.1, I do not update this repository frequently precisely to avoid unofficial releases before the official releases of Winlator.

# Frequently Asked Questions (FAQ)
## Do I need to uninstall the current winlator version before installing this mod?
- Yeah, fresh install are needed if you want to proceed to the mod to avoid corrupted system files
## So, when the mod update released and I want to update it, do I still needed to reinstall the winlator?
- It's highly recommended to reinstall the winlator when the mod update released, you can also directly install the new mod update but the current container from previous version can't be used, so that's why it's important to reinstall
## I see there's 2 apk in the proot releases, so what is the difference between bench and regular apk? 
- Bench is for the user that have 2 or more winlator. It's suitable for the user to compare other winlator. While, without bench is for the user that don't have/install any winlator yet.
- Bench apk are also to be used for speeding the performance of the game
## And why the bench are no longer available in glibc 7.1.3 and 10.0?
- For your information, any custom package name included bench are not supported in glibc version, sadly glibc only supports regular apk only. If I were made it, the apk never worked
## Help, the game doesn't work at me!
- First, try changing the turnip or dxvk if using Snapdragon or make sure you have read the system requirement above. Secondly, try changing the box64 preset, some game need a specific preset. Or if it never worked, maybe the game are not playable yet in winlator, so wait for future updates of winlator
## Help, my phone anti-virus detected that this winlator mod have a viruses! So is it still safe to use?
- Never believe to the system anti-virus especially Google Play Protect and Xiaomi Anti-virus, this anti-virus actually are really sensitive to the emulation because most of all emulators detect the viruses in this anti-virus, so no need to worry. I also scanned the VirusTotal before I release the apk and it safe to use. But what is the most important is don't spread the news about malware in the winlator, if you found a malware, please contact me immediately and I will try to removed that shit
## I want to request some features to be added in the mod, can you proceed, please?
- I actually modified the winlator by using my phone and I still can't put some features in the winlator because it needs a pc to do that
## So sad that you modify the mod by using phone, btw I want to give some donations to help your mod
- I actually not ready yet to get monetized in my project that I made, you know I still below the age, and I don't have my own credit card or PayPal account to received any donations from y'all. Also I made this project is free to everyone and I want to see the users are happy and enjoyable when using my mod

# Credits and Third-party apps
- GLIBC Patches by [Termux Pacman](https://github.com/termux-pacman/glibc-packages)
- Ubuntu RootFs ([Focal Fossa](https://releases.ubuntu.com/focal))
- Wine ([winehq.org](https://www.winehq.org/))
- Box86/Box64 by [ptitseb](https://github.com/ptitSeb)
- PRoot ([proot-me.github.io](https://proot-me.github.io))
- Mesa3D (Turnip, Zink, VirGL) ([mesa3d.org](https://www.mesa3d.org))
- DXVK ([github.com/doitsujin/dxvk](https://github.com/doitsujin/dxvk))
- DXVK gplasync ([gitlab.com/Ph42oN/dxvk-gplasync](https://gitlab.com/Ph42oN/dxvk-gplasync))​
- D8VK ([github.com/AlpyneDreams/d8vk](https://github.com/AlpyneDreams/d8vk))
- CNC DDraw ([github.com/FunkyFr3sh/cnc-ddraw](https://github.com/FunkyFr3sh/cnc-ddraw))
- ([Winlator Turnip Driver](https://github.com/K11MCH1/WinlatorTurnipDrivers))​ by K11MCH1
- ([Box64 Proot](https://github.com/ryanfortner/box64-debs)) by ryanfortner
- ([Ajay prefix](https://github.com/ajay9634/Ajay-prefix))​
- Some advice and help by AI such as ChatGPT, Google Gemini and DeepSeek AI

Many thanks to [ptitSeb](https://github.com/ptitSeb), [Danylo](https://blogs.igalia.com/dpiliaiev/tags/mesa/), [Max Ivan](https://github.com/Maxython), [Twaik Yont](https://github.com/twaik), [alexvorxx](https://github.com/alexvorxx) and others.<br>
Thanks to all the people who have been contributed in this project that I couldn't mentioned it at here and also to those who believe and trusted this project from the beginning until today
