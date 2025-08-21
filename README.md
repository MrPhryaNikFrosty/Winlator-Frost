<p align="center">
	<img src="logo.png" width="376" height="128" alt="Winlator-Frost Logo" /> 

<div align="center">

[![Frost SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&pause=1000&center=true&vCenter=true&random=true&width=500&lines=Welcome+to+the+Winlator%40Frost+Github!;If+you+like+the+mod%2C+please+star+the+repo!;Established+since+2023,+No.1+Winlator+mod;Modded+with+%E2%9D%A4%EF%B8%8F+by+Tanakorn+and+Jay)](https://git.io/typing-svg)

</div>
<p align="center">
  <img src="https://img.shields.io/github/downloads/MrPhryaNikFrosty/Winlator-Frost/total" alt="Total Downloads" width="150">
</p>

----

# About Winlator@Frost
Winlator@Frost is a simple and basic modification of the original winlator by [Brunodev85](https://github.com/brunodev85/winlator), offering many additional contents such as Box64, DXVK, Turnip and wide variety of box64 env to improve performance. It aims to run some Windows applications by using Wine and Box64 as the main core in order to work

-----
↓ Winlator@Frost Socials ↓

[Winlator@Frost's Official YouTube channel, ran by Tanakorn](https://youtube.com/@winlatorfrostth?si=h0hQv0bwE7hY4yKO)​

[The official Winlator@Frost Discord Server, ran by Tanakorn and Jay](https://discord.gg/Q74CNHJnq2)

[Winlator@Frost's official Instagram account, ran by Tanakorn](https://www.instagram.com/winlator_frost_official.th?igsh=ZDdyOXB2OWlqZHR3)

[Winlator@Frost's official TikTok account, ran by Tanakorn](https://www.tiktok.com/@winlatorfrostofficial.th?_t=ZS-8y1MU8YsJmh&_r=1)

[Winlator@Frost's official Telegram channel, ran by Jay and Tanakorn](https://t.me/winfrost)

----

# Winlator@Frost Installation
1. Download and install any versions of Frost from [GitHub Releases](https://github.com/MrPhryaNikFrosty/Winlator-Frost/releases)
2. Launch the app and wait for the installation process to finish
3. Now you can enjoy your favorite pc games! But please make sure that you select the renderer based on your phone chips, you can read the system requirements below to learn more.
4. Star the repository!

- You can also try out Winlator@Frost GlibC 7.1.3 version from the releases and tags
- For older or legacy version of Winlator@Frost which are not available in Github releases can be able to access it at [here](https://mega.nz/folder/dQtDzI7a#47pl7EQ8AWwY2jV-665ikQ)

# Testing Videos of Winlator@Frost.
----
- Test on Honor X9b (SD 6 GEN 1)
[![Play on Youtube](https://i.ytimg.com/vi/yT8HeDDXYNk/hqdefault.jpg?sqp=-oaymwEnCNACELwBSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLBg6mwWOnCdGRZ_j0fr4rOyc_4oyQ)](https://youtu.be/yT8HeDDXYNk?si=WqEvuNUsxdjElomr)

- Test on Poco F3 (SD 870)
[![Play on Youtube](https://i.ytimg.com/vi/lWRubu4LITg/hqdefault.jpg?sqp=-oaymwFBCNACELwBSFryq4qpAzMIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB8AEB-AH-CYAC0AWKAgwIABABGC4gaChyMA8=&rs=AOn4CLCiWNAFDSSx8jICLbdoVhfUGxnDoA)](https://youtu.be/lWRubu4LITg?si=u0v8kX7HiKpdeo-1)

- Test on Honor Magic 5 (SD 8 GEN 2)
[![Play on Youtube](https://i.ytimg.com/vi/lRmnb_3untM/hqdefault.jpg?sqp=-oaymwEnCNACELwBSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLD2qDb4xNO8AYoV0yAqvZoG2o-PkQ)](https://youtu.be/lRmnb_3untM?si=8bMl1BxodFHgOAnm)

- Test on Poco X5 (SD 695)
[![Play on Youtube](https://i.ytimg.com/vi_webp/yQl-OPTIb3E/hqdefault.webp)](https://youtu.be/yQl-OPTIb3E?si=QcSX-oz1CfWwVmXb)

- For more videos, search Winlator@Frost on YouTube or check out the official tests by Tanakorn via the YouTube channel!

----

# Useful Tips & Features of Winlator@Frost

- If you are experiencing performance issues, try changing the preset for Box86/Box64 in Container Settings -> Advanced Tab.
- For applications that use .NET Framework, try installing Wine Mono found in Start Menu -> System Tools.
- If some older games don't open, try adding the environment variable MESA_EXTENSION_MAX_YEAR=2003 in Container Settings -> Environment Variables. If it already exist, just change the year to 2003. But if you play a newer games especially unity or unreal engine games, you must change it to 2025
- Try running the games using the shortcut on the Winlator home screen, there you can define individual settings for each game.
- If you want to hide the annoying dxvk hud, please untick `devinfo`, `frametimes` and `gpuload` in the environment variables of the env `DXVK_HUD` inside the container settings. Or you can also remove the env `DXVK_HUD` in the environment variables
- If you want to use VirGL in winlator version (7.1 and newer), make sure to change the env of `MESA_GL_VERSION_OVERRIDE`from `3.3COMPAT`to `2.1` or `3.1COMPAT`
For older version of winlator (legacy) you can run it using `3.3COMPAT` or `4.0`. For Winlator 9 and 10 you can change the GL version directly without the env in the VirGL settings inside of container settings
- Enable the `BOX64_DYNAREC_WEAKBARRIER` by using 1 or 2 value can improve the performance for some 64 bit games (especially unity games)
- To display low resolution games correctly, try to enabling the `Force Fullscreen` option in the shortcut settings
- To speed up the installers, try changing the Box64 preset to `Intermediate` in Container Settings -> Advanced Tab
- You can now enable `MANGOHUD` for glibc version (7.1.3), not yet available for proot and winlator 10 version
- For some unity games especially to fix unity games from crashing, try use stability preset and add `-force-gfx-direct -​force-d3d11-singlethread` in the exec arguments inside the shortcut of the unity games
- For some unity games, the game have a problem to launch in proot, since the box64 are outdated in proot so please try in winlator glibc 7.1.3 or winlator 10 version
- If the games require an Internet connection, it's recommended to install the wine gecko and mono

#### To show the VKD3D fps hud, you can try to add this env into environment variables:
    VK_INSTANCE_LAYERS=VK_LAYER_MESA_overlay

- Please take note that the before `=` are name and after it are value
- Currently it only worked in glibc 7.1.3, some reports that it still doesn't work in proot and winlator 10 version

# System requirement for Winlator@Frost
## Device
- Android: 8.0 or newer
- RAM: 3 ~ 4 GB are minimum requirements but 6 GB are recommended
- ROM: 64 GB are minimum or depending on the games that you're going to play but it's highly recommended to have at least 128 GB and SD Card are also supported but some type of SD Card doesn't recognize as drive in winlator
- Architecture: arm64-v8a are required

## Graphics Driver for device GPU
- `Turnip` - Only for Snapdragon that has `Adreno 6xx and 7xx` only at the moment. `Adreno 8xx (8 elite, 8s gen 4, 7s gen 3, 6 gen 4) and old Adreno 5xx or below` are not working. You have to use VirGL or Vortek if you're using this type of GPU.
- `Vortek` - Universal, all GPUs will work especially for Adreno 8xx which doesn't support turnip can use this driver. This driver are also work in Mali GPUs but not all Mali GPUs are works. All DX8 and DX9 games doesn't have any problem with this renderer but there's some of DX10 and DX11 games are working and sadly DX12 games with vkd3d are not working
- `VirGL` - Universal, all GPUs `such as Mali, PowerVR, Xclipse, etc and old Adreno 5xx and below or Adreno 8xx` might work with VirGL but only Ddraw, DX8 or DX9 games and OpenGL 4.0 below games are working
- `LLVMPIPE` - Software renderer, work at almost all the type of chipset but it very slow and have very worst performance. Not recommended it to play games as this renderer use CPU and not GPU, so only apps like browser, office or utility apps are working by using this renderer. This driver is only available in the legacy version (winlator 5 and older)


# Information
This project has been in constant development since version 1.0, the current app source code is up to version 7.1, I do not update this repository frequently precisely to avoid unofficial releases before the official releases of Winlator.

# Frequently Asked Questions (FAQ)
## Do I need to uninstall the current winlator version before installing this mod?
- Yes. fresh install is needed if you want to proceed to the mod to avoid corrupted system files
## So, when the mod update is released and I want to update it, do I still needed to reinstall the winlator?
- It's highly recommended to reinstall the winlator when the mod update released, you can also directly install the new mod update but the current container from previous version can't be used, so that's why it's important to reinstall
## I see there are 2 apks in the proot releases, so what is the difference between bench and regular apk? 
- Bench is for the user that have 2 or more winlator. It's suitable for the user to compare other winlator versions. While, without bench is for the user that don't have/install any winlator yet.
- Bench apks are also to be used for speeding the performance of the game for certain phones
## And why the bench are no longer available in glibc versions (aka 7.1.3 and 10)?
- For your information, any custom package name included bench is not supported in glibc version, sadly glibc only supports regular package name only. If I made it, the apk would not work.
## Help, the game doesn't work for me! It keeps crashing when I try to open it. Did you know what's the problem?
- First, try changing the turnip or dxvk if using Snapdragon or make sure you have read the system requirement above. Secondly, try changing the box64 preset, some game need a specific preset. Or if it never worked, maybe the game are not playable yet in winlator, so wait for future updates of winlator
## Help, my phone anti-virus detected that this winlator mod have a viruses! So is it still safe to use?
- Never believe to the system anti-virus especially Google Play Protect and Xiaomi Anti-virus, this anti-virus actually are really sensitive to the emulation because most of all emulators detect the viruses in this anti-virus, so no need to worry. I also scanned the VirusTotal before I release the apk and it is safe to use. But what is the most important is don't spread the news about malware in the winlator. If you found a malware, please contact me immediately and I will try to remove that shit XD
## I want to request some features to be added in the mod, can you proceed, please?
- I don't have a superpower to put some specific features because I actually modify the winlator using my phone and I still can't put some features as it requires a pc to do so.
## So sad that you modify the mod by using phone, btw I want to give some donations to help your mod
- Im actually not ready yet to get monetized in the project that I made, you know Im still below the age, and I don't have my own credit card or PayPal account to receive any donations from y'all. Also I made this project to be free to everyone and I want to see the users are happy and enjoy using my mod

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

Many thanks to [ptitSeb](https://github.com/ptitSeb), [Danylo](https://blogs.igalia.com/dpiliaiev/tags/mesa/), [Max Ivan](https://github.com/Maxython), [Twaik Yont](https://github.com/twaik), [alexvorxx](https://github.com/alexvorxx), [Jay](https://github.com/jacojay) and others.<br>
Thanks to all the people who have been contributed in this project that I couldn't mentioned it at here and also to those who believe and trusted this project from the initial release until today
