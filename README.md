<p align="center">
	<img src="logo.jpg" width="376" height="128" alt="Winlator-Frost Logo" />  
</p>

# Winlator@Frost
<p align="left">
  <img src="https://img.shields.io/github/downloads/MrPhryaNikFrosty/Winlator-Frost/total" alt="Total Downloads" width="150">
</p>

Winlator@Frost is an Android application that lets you to run Windows (x86_64) applications with Wine and Box86/Box64. Originally modified from [Brunodev85](https://github.com/brunodev85/Winlator)

Subscribe My YouTube Channel [Click Here](https://youtube.com/@emuzone20?si=FUsBJ7ZmLY7Ktu1y)​

Join us in our official Discord server [Click Here](https://discord.gg/Q74CNHJnq2)​

# Winlator@Frost Installation

1. Download and install the APK (Winlator@Frost 9.0 V1.apk) from [GitHub Releases](https://github.com/MrPhryaNikFrosty/Winlator-Frost/releases)
2. Launch the app and wait for the installation process to finish
3. Now you can enjoy your favorite pc games! But please make sure that you select the renderer based on your phone chips, you can read the system requirements below to learn more
4. You can also try out our new winlator mod glibc from the releases

# Video testing of Winlator@Frost by me
----

- Test on Honor X9b (SD 6 GEN 1)
[![Play on Youtube](https://i.ytimg.com/vi_webp/g0-XafDktx0/hqdefault.webp)](https://youtu.be/g0-XafDktx0?si=x2KqbS2vvuVRDkzv)

- Test on Huawei Nova 7 (Kirin 985)
[![Play on Youtube](https://i.ytimg.com/vi_webp/XzHptfLZEpc/hqdefault.webp)](https://youtu.be/XzHptfLZEpc?si=xp4gV4pJbqmyzrYc)

- Test on Honor Magic 5 (SD 8 GEN 2)
[![Play on Youtube](https://i.ytimg.com/vi_webp/xGRzNVE6WLg/hqdefault.webp)](https://youtu.be/xGRzNVE6WLg?si=Z4tjD_rxaiJok6d4)

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
- You can now enable `MANGOHUD` for glibc version, not yet for proot
- For some unity games especially to fix unity games from crashing, try use stability preset and add `-force-gfx-direct -​force-d3d11-singlethread` in the exec arguments inside the shortcut of the unity games
- For some unity games, the games might not be able to launch in proot, so please try in glibc

# System requirement for Winlator@Frost
- Android - 8.0 or newer
- `Turnip` - For `Adreno 6xx and 7xx` only at the moment. `Adreno 8xx (8 elite, 7s gen 3), 720 (7 gen 3), and 710 (7s gen 2, 6 gen 3, 6 gen 1)` are not working. You have to use VirGL or Vortek if you're using this type of GPU.
- `Vortek` - Universal, all chips will work especially for unsupported turnip GPU but for now since it's under experimental so other chips `like Mediatek, Exynos, Kirin, Unisoc, etc` it won't work. So you have to wait for future releases of winlator
- `VirGL` - Universal, all chips `like Mediatek, Exynos, Kirin, Unisoc, etc`(include unsupport GPU of turnip adreno like in above)` might work with VirGL but you can only play DX9 games
- `LLVMPIPE` - Use this if you want to run software apps `(not for game)`

# Known issue in Winlator@Frost
- ~~Snapdragon 8s gen 3 `Adreno​ 735` container not starting issue~~ (now it's working)
- ~~Some game in Snapdragon 8 gen 3 may not working or have a performance hit~~ (try change turnip version)
- ~~Some game have a xinput issue which virtual gamepad are not working~~ (solved by using 8.0)
- Odin 2 users, external gamepad or even virtual gamepad will facing the xinput issue (like joystick issue) in glibc version
- ~~VirGL in glibc version doesn't work at all~~ (it's working by using the winlator glibc with VirGL version)
- SD card doesn't read properly in newer versions of winlator
- Fps limiter doesn't work in winlator 9.0

# What is the difference between bench and without bench apk? 
- Bench is for the user that have 2 or more winlator. It's suitable for the user to compare other winlator. While, without bench is for the user that don't have/install any winlator yet.
- Bench apk are also to be used for speeding the performance of the game

# Information
This project has been in constant development since version 1.0, the current app source code is up to version 7.1, I do not update this repository frequently precisely to avoid unofficial releases before the official releases of Winlator.

# Like my mod? 
- If you like then give us a star for this repository to support the development for this mod. Also give us feedback or suggestions in 'pull requests' if you have any suggestions to put in my mod.

# Hate my mod or think other winlator mod are better than mine?
- Yes. Exactly, my mod will become very worst at some device that are not fit with my mod. So you can try other winlator mod that are better than me (like afei, ajay, cmod, or any mod that you'd prefer). Remember, I made this winlator just for personal use only but I think it would be good if I share it to other people. But, what can I do if my winlator mod doesn't work properly in your device. Or if you're really hating me that my mod are totally bad in every update day by day just leave this repository, and I don't care it all

# Credits and Third-party apps
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

Many thanks to [ptitSeb](https://github.com/ptitSeb) (Box86/Box64), [Danylo](https://blogs.igalia.com/dpiliaiev/tags/mesa/) (Turnip), [alexvorxx](https://github.com/alexvorxx) 
(Mods/Tips) and others.
Thank you to all the people who believe in this project.
