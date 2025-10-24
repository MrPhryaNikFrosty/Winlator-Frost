package com.winlator.cmod;

import static com.winlator.cmod.core.AppUtils.showToast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.winlator.cmod.R;
import com.winlator.cmod.box86_64.rc.RCFile;
import com.winlator.cmod.box86_64.rc.RCManager;
import com.winlator.cmod.container.Container;
import com.winlator.cmod.container.ContainerManager;
import com.winlator.cmod.container.Shortcut;
import com.winlator.cmod.contentdialog.ContentDialog;
import com.winlator.cmod.contentdialog.DXVKConfigDialog;
import com.winlator.cmod.contentdialog.DebugDialog;
import com.winlator.cmod.contentdialog.GraphicsDriverConfigDialog;
import com.winlator.cmod.contentdialog.ScreenEffectDialog;
import com.winlator.cmod.contentdialog.VKD3DConfigDialog;
import com.winlator.cmod.contents.ContentProfile;
import com.winlator.cmod.contents.ContentsManager;
import com.winlator.cmod.contents.AdrenotoolsManager;
import com.winlator.cmod.core.AppUtils;
import com.winlator.cmod.core.DefaultVersion;
import com.winlator.cmod.core.EnvVars;
import com.winlator.cmod.core.EnvironmentManager;
import com.winlator.cmod.core.FileUtils;
import com.winlator.cmod.core.GPUInformation;
import com.winlator.cmod.core.KeyValueSet;
import com.winlator.cmod.core.OnExtractFileListener;
import com.winlator.cmod.core.PreloaderDialog;
import com.winlator.cmod.core.ProcessHelper;
import com.winlator.cmod.core.StringUtils;
import com.winlator.cmod.core.TarCompressorUtils;
import com.winlator.cmod.core.Win32AppWorkarounds;
import com.winlator.cmod.core.WineInfo;
import com.winlator.cmod.core.WineRegistryEditor;
import com.winlator.cmod.core.WineRequestHandler;
import com.winlator.cmod.core.WineStartMenuCreator;
import com.winlator.cmod.core.WineThemeManager;
import com.winlator.cmod.core.WineUtils;
import com.winlator.cmod.fexcore.FEXCoreManager;
import com.winlator.cmod.inputcontrols.ControlsProfile;
import com.winlator.cmod.inputcontrols.ExternalController;
import com.winlator.cmod.inputcontrols.InputControlsManager;
import com.winlator.cmod.math.Mathf;
import com.winlator.cmod.math.XForm;
import com.winlator.cmod.midi.MidiHandler;
import com.winlator.cmod.midi.MidiManager;
import com.winlator.cmod.renderer.GLRenderer;
import com.winlator.cmod.renderer.effects.CRTEffect;
import com.winlator.cmod.renderer.effects.ColorEffect;
import com.winlator.cmod.renderer.effects.FXAAEffect;
import com.winlator.cmod.renderer.effects.NTSCCombinedEffect;
import com.winlator.cmod.renderer.effects.ToonEffect;
import com.winlator.cmod.widget.FrameRating;
import com.winlator.cmod.widget.InputControlsView;
import com.winlator.cmod.widget.LogView;
import com.winlator.cmod.widget.MagnifierView;
import com.winlator.cmod.widget.TouchpadView;
import com.winlator.cmod.widget.WinetricksFloatingView;
import com.winlator.cmod.widget.XServerView;
import com.winlator.cmod.winhandler.MouseEventFlags;
import com.winlator.cmod.winhandler.TaskManagerDialog;
import com.winlator.cmod.winhandler.WinHandler;
import com.winlator.cmod.xconnector.UnixSocketConfig;
import com.winlator.cmod.xenvironment.ImageFs;
import com.winlator.cmod.xenvironment.XEnvironment;
import com.winlator.cmod.xenvironment.components.ALSAServerComponent;
import com.winlator.cmod.xenvironment.components.BionicProgramLauncherComponent;
import com.winlator.cmod.xenvironment.components.GlibcProgramLauncherComponent;
import com.winlator.cmod.xenvironment.components.GuestProgramLauncherComponent;
import com.winlator.cmod.xenvironment.components.NetworkInfoUpdateComponent;
import com.winlator.cmod.xenvironment.components.PulseAudioComponent;
import com.winlator.cmod.xenvironment.components.SysVSharedMemoryComponent;
import com.winlator.cmod.xenvironment.components.XServerComponent;
import com.winlator.cmod.xserver.Pointer;
import com.winlator.cmod.xserver.Property;
import com.winlator.cmod.xserver.ScreenInfo;
import com.winlator.cmod.xserver.Window;
import com.winlator.cmod.xserver.WindowManager;
import com.winlator.cmod.xserver.XServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;

public class XServerDisplayActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private XServerView xServerView;
    private InputControlsView inputControlsView;
    private TouchpadView touchpadView;
    private XEnvironment environment;
    private DrawerLayout drawerLayout;
    private ContainerManager containerManager;
    protected Container container;
    private XServer xServer;
    private InputControlsManager inputControlsManager;
    private ImageFs imageFs;
    private FrameRating frameRating = null;
    private Runnable editInputControlsCallback;
    private Shortcut shortcut;
    private String graphicsDriver = Container.DEFAULT_GRAPHICS_DRIVER;
    private HashMap<String, String> graphicsDriverConfig;
    private String audioDriver = Container.DEFAULT_AUDIO_DRIVER;
    private String emulator = Container.DEFAULT_EMULATOR;
    private String dxwrapper = Container.DEFAULT_DXWRAPPER;
    private String ddrawrapper = Container.DEFAULT_DDRAWRAPPER;
    private KeyValueSet dxwrapperConfig;
    private String startupSelection;
    private WineInfo wineInfo;
    private final EnvVars envVars = new EnvVars();
    private boolean firstTimeBoot = false;
    private SharedPreferences preferences;
    private OnExtractFileListener onExtractFileListener;
    private WinHandler winHandler;
    private WineRequestHandler wineRequestHandler;
    private float globalCursorSpeed = 1.0f;
    private MagnifierView magnifierView;
    private DebugDialog debugDialog;
    private short taskAffinityMask = 0;
    private short taskAffinityMaskWoW64 = 0;
    private int frameRatingWindowId = -1;
    private boolean pointerCaptureRequested = false; // Flag to track if pointer capture was requested
    private final float[] xform = XForm.getInstance();
    private ContentsManager contentsManager;
    private boolean navigationFocused = false;
    private MidiHandler midiHandler;
    private String midiSoundFont = "";
    private String lc_all = "";
    PreloaderDialog preloaderDialog = null;
    private Runnable configChangedCallback = null;
    private boolean isPaused = false;

    // Inside the XServerDisplayActivity class
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private ExternalController controller;

    // Playtime stats tracking
    private long startTime;
    private SharedPreferences playtimePrefs;
    private String shortcutName;
    private Handler handler;
    private Runnable savePlaytimeRunnable;
    private static final long SAVE_INTERVAL_MS = 1000;

    private Handler  timeoutHandler = new Handler(Looper.getMainLooper());
    private Runnable hideControlsRunnable;

    private boolean isDarkMode;

    private String screenEffectProfile;

    private GlibcProgramLauncherComponent glibcLauncher; // Reference to GlibcProgramLauncherComponent
    private BionicProgramLauncherComponent bionicLauncher; // Reference to BionicProgramLauncherComponent
    private FileObserver restartTriggerObserver;

    private Win32AppWorkarounds win32AppWorkarounds;
    private EnvVars overrideEnvVars;

    private WinetricksFloatingView winetricksFloatingView;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (configChangedCallback != null) {
            configChangedCallback.run();
            configChangedCallback = null;
        }
    }


    private final SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float gyroX = event.values[0]; // Rotation around the X-axis
                float gyroY = event.values[1]; // Rotation around the Y-axis

                winHandler.updateGyroData(gyroX, gyroY); // Send gyro data to WinHandler
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // No action needed
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.hideSystemUI(this);
        AppUtils.keepScreenOn(this);
        setContentView(R.layout.xserver_display_activity);

//        String RESTART_TRIGGER_PATH = "/data/user/0/com.winlator/files/imagefs/tmp/winlator_restart_trigger";
//        String RESTART_TRIGGER_DIR = "/data/user/0/com.winlator/files/imagefs/tmp/";
//
//        restartTriggerObserver = new FileObserver(RESTART_TRIGGER_DIR, FileObserver.CLOSE_WRITE | FileObserver.CREATE) {
//            @Override
//            public void onEvent(int event, String path) {
//                if (path != null && path.equals("winlator_restart_trigger") && event == FileObserver.CLOSE_WRITE) {
//                    Log.d("XServerDisplayActivity", "Detected trigger file creation/modification, restarting wineserver.");
//                    if (glibcLauncher != null) {
//                        glibcLauncher.restartWineServer();
//                        //setupWineSystemFiles();
//                        setupXEnvironment();
//                    } else {
//                        Log.e("XServerDisplayActivity", "glibcLauncher is null; cannot restart wineserver.");
//                    }
//                }
//            }
//        };
//        restartTriggerObserver.startWatching();


//        // Test file creation right after starting the observer
//        try {
//            File testFile = new File(RESTART_TRIGGER_PATH);
//            testFile.createNewFile();
//            Log.d("XServerDisplayActivity", "Test file created to trigger observer.");
//        } catch (IOException e) {
//            Log.e("XServerDisplayActivity", "Failed to create test file: " + e.getMessage());
//        }



        final PreloaderDialog preloaderDialog = new PreloaderDialog(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        // Check for Dark Mode
        isDarkMode = preferences.getBoolean("dark_mode", false);

        boolean isOpenWithAndroidBrowser = preferences.getBoolean("open_with_android_browser", false);
        boolean isShareAndroidClipboard = preferences.getBoolean("share_android_clipboard", false);

        // Initialize the WinHandler after context is set up
        winHandler = new WinHandler(this);
        winHandler.initializeController();
        controller = winHandler.getCurrentController();

        if (isOpenWithAndroidBrowser || isShareAndroidClipboard)
            wineRequestHandler = new WineRequestHandler(this);

        if (controller != null) {
            int triggerType = preferences.getInt("trigger_type", ExternalController.TRIGGER_IS_AXIS); // Default to TRIGGER_IS_AXIS
            controller.setTriggerType((byte) triggerType); // Cast to byte if needed
        }



        // Check if xinputDisabled extra is passed
        boolean xinputDisabledFromShortcut = false;




        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        boolean gyroEnabled = preferences.getBoolean("gyro_enabled", true);

        if (gyroEnabled) {
            // Register the sensor event listener
            sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
        }



        // Record the start time
        startTime = System.currentTimeMillis();

        // Initialize handler for periodic saving
        handler = new Handler(Looper.getMainLooper());
        savePlaytimeRunnable = new Runnable() {
            @Override
            public void run() {
                savePlaytimeData();
                handler.postDelayed(this, SAVE_INTERVAL_MS);
            }
        };
        handler.postDelayed(savePlaytimeRunnable, SAVE_INTERVAL_MS);


        // Handler and Runnable to manage timeout for hiding controls

        boolean isTimeoutEnabled = preferences.getBoolean("touchscreen_timeout_enabled", true);

        hideControlsRunnable = () -> {
            if (isTimeoutEnabled) {
                inputControlsView.setVisibility(View.GONE);
                Log.d("XServerDisplayActivity", "Touchscreen controls hidden after timeout.");
            }
        };


        contentsManager = new ContentsManager(this);
        contentsManager.syncContents();

        drawerLayout = findViewById(R.id.DrawerLayout);
        drawerLayout.setOnApplyWindowInsetsListener((view, windowInsets) -> windowInsets.replaceSystemWindowInsets(0, 0, 0, 0));
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        NavigationView navigationView = findViewById(R.id.NavigationView);

        if (isDarkMode) {
            navigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.white));
            navigationView.setBackgroundResource(R.color.content_dialog_background_dark);
        }

        boolean enableLogs = preferences.getBoolean("enable_wine_debug", false) || preferences.getBoolean("enable_box86_64_logs", false);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.main_menu_logs).setVisible(enableLogs);
        if (XrActivity.isEnabled(this)) menu.findItem(R.id.main_menu_magnifier).setVisible(false);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_ARROW));
        navigationView.setOnFocusChangeListener((v, hasFocus) -> navigationFocused = hasFocus);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                navigationView.requestFocus();
            }
        });

        imageFs = ImageFs.find(this);

        String screenSize = Container.DEFAULT_SCREEN_SIZE;
        if (!isGenerateWineprefix()) {
            containerManager = new ContainerManager(this);
            container = containerManager.getContainerById(getIntent().getIntExtra("container_id", 0));
//            containerManager.activateContainer(container);

            // Log shortcut_path
            String shortcutPath = getIntent().getStringExtra("shortcut_path");
            Log.d("XServerDisplayActivity", "Shortcut Path: " + shortcutPath);


            // Determine container ID
            int containerId = getIntent().getIntExtra("container_id", 0);
            Log.d("XServerDisplayActivity", "Container ID from Intent: " + containerId);
            if (containerId == 0) {
                Log.d("XServerDisplayActivity", "Container ID is 0, attempting to parse from .desktop file");
                // Proceed with .desktop file parsing
            }


            // If container_id is 0, read from the .desktop file
            if (containerId == 0 && shortcutPath != null && !shortcutPath.isEmpty()) {
                File shortcutFile = new File(shortcutPath);
                containerId = parseContainerIdFromDesktopFile(shortcutFile);
                Log.d("XServerDisplayActivity", "Parsed Container ID from .desktop file: " + containerId);
            }


            // Initialize playtime tracking
            playtimePrefs = getSharedPreferences("playtime_stats", MODE_PRIVATE);
            shortcutName = getIntent().getStringExtra("shortcut_name");

            // Ensure shortcutPath is not null before proceeding
            if (shortcutPath != null && !shortcutPath.isEmpty()) {
                if (shortcutName == null || shortcutName.isEmpty()) {
                    shortcutName = parseShortcutNameFromDesktopFile(new File(shortcutPath));
                    Log.d("XServerDisplayActivity", "Parsed Shortcut Name from .desktop file: " + shortcutName);
                }
            } else {
                Log.d("XServerDisplayActivity", "No shortcut path provided, skipping shortcut parsing.");
            }


            // Increment play count at the start of a session
            incrementPlayCount();

            // Log the final container_id
            Log.d("XServerDisplayActivity", "Final Container ID: " + containerId);

            // Retrieve the container and check if it's null
            container = containerManager.getContainerById(containerId);

            if (container == null) {
                Log.e("XServerDisplayActivity", "Failed to retrieve container with ID: " + containerId);
                finish();  // Gracefully exit the activity to avoid crashing
                return;
            }

            containerManager.activateContainer(container);

            if (shortcutPath != null && !shortcutPath.isEmpty()) {
                shortcut = new Shortcut(container, new File(shortcutPath));
            }

            // Initialize Win32AppWorkarounds
            win32AppWorkarounds = new Win32AppWorkarounds(this);

            taskAffinityMask = (short) ProcessHelper.getAffinityMask(container.getCPUList(true));
            taskAffinityMaskWoW64 = (short) ProcessHelper.getAffinityMask(container.getCPUListWoW64(true));

            if (shortcut != null) {
                taskAffinityMask = (short) ProcessHelper.getAffinityMask(shortcut.getExtra("cpuList", container.getCPUList(true)));
                taskAffinityMaskWoW64 = (short) ProcessHelper.getAffinityMask(shortcut.getExtra("cpuListWoW64", container.getCPUListWoW64(true)));
            }

            win32AppWorkarounds.setTaskAffinityMask(taskAffinityMask);
            win32AppWorkarounds.setTaskAffinityMaskWoW64(taskAffinityMaskWoW64);

            // Determine the class name for the startup workarounds
            String wmClass = shortcut != null ? shortcut.getExtra("wmClass", "") : "";
            Log.d("XServerDisplayActivity", "Startup wmClass: " + wmClass);

            if (!wmClass.isEmpty()) {
                // Apply startup workarounds based on wmClass
                win32AppWorkarounds.applyStartupWorkarounds(wmClass);
            } else {
                // Fallback: Use the executable name for workarounds
                String execPath = getIntent().getStringExtra("exec_path");
                Log.d("XServerDisplayActivity", "Startup execPath: " + execPath);

                if (execPath != null && !execPath.isEmpty()) {
                    String execName = FileUtils.getName(execPath);
                    Log.d("XServerDisplayActivity", "Startup execName: " + execName);

                    win32AppWorkarounds.applyStartupWorkarounds(execName);
                } else {
                    Log.w("XServerDisplayActivity", "No wmClass or execPath provided for startup workarounds.");
                }
            }

            firstTimeBoot = container.getExtra("appVersion").isEmpty();

            String wineVersion = container.getWineVersion();
            wineInfo = WineInfo.fromIdentifier(this, wineVersion);

            imageFs.setWinePath(wineInfo.path);

            ProcessHelper.removeAllDebugCallbacks();
            if (enableLogs) {
                LogView.setFilename(getExecutable());
                ProcessHelper.addDebugCallback(debugDialog = new DebugDialog(this));
            }

            // Retrieve secondary executable and delay
            String secondaryExec = shortcut != null ? shortcut.getExtra("secondaryExec") : null;
            int execDelay = shortcut != null ? Integer.parseInt(shortcut.getExtra("execDelay", "0")) : 0;

            // Debug logging for secondaryExec and execDelay
            Log.d("XServerDisplayActivity", "Secondary Exec: " + secondaryExec);
            Log.d("XServerDisplayActivity", "Execution Delay: " + execDelay);

            // If a secondary executable is specified, schedule it
            if (secondaryExec != null && !secondaryExec.isEmpty() && execDelay > 0) {
                scheduleSecondaryExecution(secondaryExec, execDelay);
                Log.d("XServerDisplayActivity", "Scheduling secondary execution: " + secondaryExec + " with delay: " + execDelay);
            } else {
                Log.d("XServerDisplayActivity", "No valid secondary executable or delay is zero, skipping scheduling.");
            }

            graphicsDriver = container.getGraphicsDriver();
            String graphicsDriverConfig = container.getGraphicsDriverConfig();
            audioDriver = container.getAudioDriver();
            emulator = container.getEmulator();
            midiSoundFont = container.getMIDISoundFont();
            dxwrapper = container.getDXWrapper();
            ddrawrapper = container.getDDrawWrapper();
            String dxwrapperConfig = container.getDXWrapperConfig();
            screenSize = container.getScreenSize();
            winHandler.setInputType((byte) container.getInputType());
            lc_all = container.getLC_ALL();

            // Log the entire intent to verify the extras
            Intent intent = getIntent();
            Log.d("XServerDisplayActivity", "Intent Extras: " + intent.getExtras());

            if (shortcut != null) {
                graphicsDriver = shortcut.getExtra("graphicsDriver", container.getGraphicsDriver());
                graphicsDriverConfig = shortcut.getExtra("graphicsDriverConfig", container.getGraphicsDriverConfig());
                audioDriver = shortcut.getExtra("audioDriver", container.getAudioDriver());
                emulator = shortcut.getExtra("emulator", container.getEmulator());
                dxwrapper = shortcut.getExtra("dxwrapper", container.getDXWrapper());
                ddrawrapper = shortcut.getExtra("ddrawrapper", container.getDDrawWrapper());
                dxwrapperConfig = shortcut.getExtra("dxwrapperConfig", container.getDXWrapperConfig());
                screenSize = shortcut.getExtra("screenSize", container.getScreenSize());
                lc_all = shortcut.getExtra("lc_all", container.getLC_ALL());
                String inputType = shortcut.getExtra("inputType");
                if (!inputType.isEmpty()) winHandler.setInputType(Byte.parseByte(inputType));
                String xinputDisabledString = shortcut.getExtra("disableXinput", "false");
                xinputDisabledFromShortcut = parseBoolean(xinputDisabledString);
                // Pass the value to WinHandler
                winHandler.setXInputDisabled(xinputDisabledFromShortcut);
                Log.d("XServerDisplayActivity", "XInput Disabled from Shortcut: " + xinputDisabledFromShortcut);
            }

            this.graphicsDriverConfig = GraphicsDriverConfigDialog.parseGraphicsDriverConfig(graphicsDriverConfig);

            if (dxwrapper.equals("dxvk") || dxwrapper.equals("vkd3d")) {
                this.dxwrapperConfig = DXVKConfigDialog.parseConfig(dxwrapperConfig);
            }



            if (!wineInfo.isWin64()) {
                onExtractFileListener = (file, size) -> {
                    String path = file.getPath();
                    if (path.contains("system32/")) return null;
                    return new File(path.replace("syswow64/", "system32/"));
                };
            }
        }

        preloaderDialog.show(R.string.starting_up);


        inputControlsManager = new InputControlsManager(this);
        xServer = new XServer(new ScreenInfo(screenSize));
        xServer.setWinHandler(winHandler);

        boolean[] winStarted = {false};

//        startProcessDetection();

        // Add the OnWindowModificationListener for dynamic workarounds
        xServer.windowManager.addOnWindowModificationListener(new WindowManager.OnWindowModificationListener() {
            @Override
            public void onUpdateWindowContent(Window window) {
                if (!winStarted[0] && window.isApplicationWindow()) {
                    xServerView.getRenderer().setCursorVisible(true);
                    preloaderDialog.closeOnUiThread();
                    winStarted[0] = true;
                }
                    
                if (frameRatingWindowId == window.id) frameRating.update();
            }
           
            @Override
            public void onMapWindow(Window window) {
                // Log the class name of the mapped window
                Log.d("XServerDisplayActivity", "onMapWindow: Detected window className: " + window.getClassName());

                // Apply task affinity and other workarounds
                if (win32AppWorkarounds != null) {
                    // Apply dynamic workarounds based on the window class name
                    win32AppWorkarounds.applyStartupWorkarounds(window.getClassName());

                    // Assign CPU affinity for the process
                    win32AppWorkarounds.assignTaskAffinity(window);
                } else {
                    Log.e("XServerDisplayActivity", "win32AppWorkarounds is null in onMapWindow.");
                }
            }

            @Override
            public void onModifyWindowProperty(Window window, Property property) {
                changeFrameRatingVisibility(window, property);
            }    

            @Override
            public void onUnmapWindow(Window window) {
                changeFrameRatingVisibility(window, null);
            }
        });

        if (!midiSoundFont.equals("")) {
            InputStream in = null;
            InputStream finalIn = in;
            MidiManager.OnMidiLoadedCallback callback = new MidiManager.OnMidiLoadedCallback() {
                @Override
                public void onSuccess(SF2Soundbank soundbank) {
                    midiHandler = new MidiHandler();
                    midiHandler.setSoundBank(soundbank);
                    midiHandler.start();
                }

                @Override
                public void onFailed(Exception e) {
                    try {
                        finalIn.close();
                    } catch (Exception e2) {}
                }
            };
            try {
                if (midiSoundFont.equals(MidiManager.DEFAULT_SF2_FILE)) {
                    in = getAssets().open(MidiManager.SF2_ASSETS_DIR + "/" + midiSoundFont);
                    MidiManager.load(in, callback);
                } else
                    MidiManager.load(new File(MidiManager.getSoundFontDir(this), midiSoundFont), callback);
            } catch (Exception e) {}
        }

        // Check if a profile is defined by the shortcut
        String controlsProfile = shortcut != null ? shortcut.getExtra("controlsProfile", "") : "";

        Runnable runnable = () -> {
            setupUI();
            if (controlsProfile.isEmpty()) {
                // No profile defined, run the simulated dialog confirmation for input controls
                simulateConfirmInputControlsDialog();
            }
            Executors.newSingleThreadExecutor().execute(() -> {
                    
                if (!isGenerateWineprefix()) {

                    setupWineSystemFiles();
                    extractGraphicsDriverFiles();
//                    container.setGraphicsDriverVersion(originalContainerDriverVersion);
//                    container.saveData();
                    changeWineAudioDriver();
                    if (container != null) {
                        if (!wineInfo.isArm64EC())
                            envVars.put("HODLL", "wow64cpu.dll");
                        else if (emulator.toLowerCase().equals("fexcore"))
                            envVars.put("HODLL", "libwow64fex.dll");
                        else
                            envVars.put("HODLL", "wowbox64.dll");
                        if (isOpenWithAndroidBrowser)
                            envVars.put("WINE_OPEN_WITH_ANDROID_BROWSER", "1");
                        if (isShareAndroidClipboard) {
                            envVars.put("WINE_FROM_ANDROID_CLIPBOARD", "1");
                            envVars.put("WINE_TO_ANDROID_CLIPBOARD", "1");
                        }
                    }
//                    runWinetricksAfterSetup();
                    // Run winetricks before setting up the X environment
//                    runWinetricks("--force vcrun2010");  // Replace with the desired winetricks arguments

                }
                try {
                    setupXEnvironment();
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }

//                runWinetricksAfterSetup();
                // Run winetricks after setting up the X environment
//                runWinetricks("--force vcrun2010");  // Replace with the desired winetricks arguments
            });
        };

        if (xServer.screenInfo.height > xServer.screenInfo.width) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            configChangedCallback = runnable;
        } else
              runnable.run();
    }

    // Method to parse container_id from .desktop file
    private int parseContainerIdFromDesktopFile(File desktopFile) {
        int containerId = 0;
        if (desktopFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(desktopFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("container_id:")) {
                        containerId = Integer.parseInt(line.split(":")[1].trim());
                        break;
                    }
                }
            } catch (IOException | NumberFormatException e) {
                Log.e("XServerDisplayActivity", "Error parsing container_id from .desktop file", e);
            }
        }
        return containerId;
    }

    private boolean parseBoolean(String value) {
        // Return true for "true", "1", "yes" (case-insensitive)
        if ("true".equalsIgnoreCase(value) || "1".equals(value) || "yes".equalsIgnoreCase(value)) {
            return true;
        }
        // Return false for any other value, including "false", "0", "no"
        return false;
    }





    // Inside XServerDisplayActivity class
    private void handleCapturedPointer(MotionEvent event) {
        boolean handled = false;

        int actionButton = event.getActionButton();
        switch (event.getAction()) {
            case MotionEvent.ACTION_BUTTON_PRESS:
                if (actionButton == MotionEvent.BUTTON_PRIMARY) {
                    if (xServer.isForceMouseControl() || xServer.isRelativeMouseMovement())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.LEFTDOWN, 0, 0, 0);
                    else
                        xServer.injectPointerButtonPress(Pointer.Button.BUTTON_LEFT);
                } else if (actionButton == MotionEvent.BUTTON_SECONDARY) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.RIGHTDOWN, 0, 0, 0);
                    else
                        xServer.injectPointerButtonPress(Pointer.Button.BUTTON_RIGHT);
                } else if (actionButton == MotionEvent.BUTTON_TERTIARY) {
                    if (xServer.isForceMouseControl() || xServer.isRelativeMouseMovement())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.MIDDLEDOWN, 0, 0, 0);
                    else
                        xServer.injectPointerButtonPress(Pointer.Button.BUTTON_MIDDLE); // Add this line for middle mouse button press
                }
                handled = true;
                break;
            case MotionEvent.ACTION_BUTTON_RELEASE:
                if (actionButton == MotionEvent.BUTTON_PRIMARY) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.LEFTUP, 0, 0, 0);
                    else
                        xServer.injectPointerButtonRelease(Pointer.Button.BUTTON_LEFT);
                } else if (actionButton == MotionEvent.BUTTON_SECONDARY) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.RIGHTUP, 0, 0, 0);
                    else
                        xServer.injectPointerButtonRelease(Pointer.Button.BUTTON_RIGHT);
                } else if (actionButton == MotionEvent.BUTTON_TERTIARY) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.MIDDLEUP, 0, 0, 0);
                    else
                        xServer.injectPointerButtonRelease(Pointer.Button.BUTTON_MIDDLE); // Add this line for middle mouse button release
                }
                handled = true;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_HOVER_MOVE:
                float[] transformedPoint = XForm.transformPoint(xform, event.getX(), event.getY());
                if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                    xServer.getWinHandler().mouseEvent(MouseEventFlags.MOVE, (int)transformedPoint[0], (int)transformedPoint[1], 0);
                else
                    xServer.injectPointerMove((int)transformedPoint[0], (int)transformedPoint[1]);
                handled = true;
                break;
            case MotionEvent.ACTION_SCROLL:
                float scrollY = event.getAxisValue(MotionEvent.AXIS_VSCROLL);
                if (scrollY <= -1.0f) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.WHEEL, 0, 0, (int)scrollY * 270);
                    else {
                        xServer.injectPointerButtonPress(Pointer.Button.BUTTON_SCROLL_DOWN);
                        xServer.injectPointerButtonRelease(Pointer.Button.BUTTON_SCROLL_DOWN);
                    }
                } else if (scrollY >= 1.0f) {
                    if (xServer.isRelativeMouseMovement() || xServer.isForceMouseControl())
                        xServer.getWinHandler().mouseEvent(MouseEventFlags.WHEEL, 0, 0,(int)scrollY * 270);
                    else {
                        xServer.injectPointerButtonPress(Pointer.Button.BUTTON_SCROLL_UP);
                        xServer.injectPointerButtonRelease(Pointer.Button.BUTTON_SCROLL_UP);
                    }
                }
                handled = true;
                break;
        }
    }



    //    private void setCustomCursor() {
//        View decorView = getWindow().getDecorView();
//        Bitmap transparentCursorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.transparent_cursor);
//        PointerIcon transparentCursorIcon = PointerIcon.create(transparentCursorBitmap, 0, 0);
//        decorView.setPointerIcon(transparentCursorIcon);
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.EDIT_INPUT_CONTROLS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (editInputControlsCallback != null) {
                editInputControlsCallback.run();
                editInputControlsCallback = null;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean gyroEnabled = preferences.getBoolean("gyro_enabled", true);

        if (gyroEnabled) {
            // Re-register the sensor listener when the activity is resumed
            sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
        }

        if (environment != null) {
            xServerView.onResume();
            environment.onResume();
        }
        startTime = System.currentTimeMillis();
        handler.postDelayed(savePlaytimeRunnable, SAVE_INTERVAL_MS);

    }

    @Override
    public void onPause() {
        super.onPause();
        boolean gyroEnabled = preferences.getBoolean("gyro_enabled", true);

        if (gyroEnabled) {
            // Unregister the sensor listener when the activity is paused
            sensorManager.unregisterListener(gyroListener);
        }

        // Check if we are entering Picture-in-Picture mode
        if (!isInPictureInPictureMode()) {
            // Only pause environment and xServerView if not in PiP mode
            if (environment != null) {
                environment.onPause();
                xServerView.onPause();
            }
        }

        savePlaytimeData();
        handler.removeCallbacks(savePlaytimeRunnable);
    }


    private void savePlaytimeData() {
        long endTime = System.currentTimeMillis();
        long playtime = endTime - startTime;

        // Ensure that playtime is not negative
        if (playtime < 0) {
            playtime = 0;
        }

        SharedPreferences.Editor editor = playtimePrefs.edit();
        String playtimeKey = shortcutName + "_playtime";

        // Accumulate the playtime into totalPlaytime
        long totalPlaytime = playtimePrefs.getLong(playtimeKey, 0) + playtime;
        editor.putLong(playtimeKey, totalPlaytime);
        editor.apply();

        // Reset startTime to the current time for the next interval
        startTime = System.currentTimeMillis();
    }


    private void incrementPlayCount() {
        SharedPreferences.Editor editor = playtimePrefs.edit();
        String playCountKey = shortcutName + "_play_count";
        int playCount = playtimePrefs.getInt(playCountKey, 0) + 1;
        editor.putInt(playCountKey, playCount);
        editor.apply();
    }

    private void exit() {
        if (midiHandler != null) midiHandler.stop();
        // Unregister sensor listener to avoid memory leaks
        if (sensorManager != null) sensorManager.unregisterListener(gyroListener);
        if (environment != null) environment.stopEnvironmentComponents();
        if (preloaderDialog != null && preloaderDialog.isShowing()) preloaderDialog.close();
        if (winHandler != null) winHandler.stop();
        if (wineRequestHandler != null) wineRequestHandler.stop();
        /* Gracefully terminate all running wine processes */
        ProcessHelper.terminateAllWineProcesses();
        /* Wait until all processes have gracefully terminated, forcefully killing them only after a certain amount of time */
        long start = System.currentTimeMillis();
        while (!ProcessHelper.listRunningWineProcesses().isEmpty()) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed >= 1500) {
                break;
            }
        }
        AppUtils.restartApplication(this);
    }

    @Override
    protected void onDestroy() {
        savePlaytimeData(); // Save on destroy
        handler.removeCallbacks(savePlaytimeRunnable);
        if (restartTriggerObserver != null) restartTriggerObserver.stopWatching();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePlaytimeData();
        handler.removeCallbacks(savePlaytimeRunnable);



    }


    @Override
    public void onBackPressed() {
        if (environment != null) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            else drawerLayout.closeDrawers();
        }
    }

    private void openXServerDrawer() {
        if (environment != null) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            else drawerLayout.closeDrawers();
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final GLRenderer renderer = xServerView.getRenderer();
        switch (item.getItemId()) {
            case R.id.main_menu_keyboard:
                AppUtils.showKeyboard(this);
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_input_controls:
                showInputControlsDialog();
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_toggle_fullscreen:
                renderer.toggleFullscreen();
                drawerLayout.closeDrawers();
                touchpadView.toggleFullscreen();
                break;
            case R.id.main_menu_pause:
                if (isPaused) {
                    ProcessHelper.resumeAllWineProcesses();
                    item.setIcon(R.drawable.icon_pause);
                }
                else {
                    ProcessHelper.pauseAllWineProcesses();
                    item.setIcon(R.drawable.icon_play);
                }
                isPaused = !isPaused;
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_pip_mode:
                enterPictureInPictureMode();
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_task_manager:
                new TaskManagerDialog(this).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_magnifier:
                if (magnifierView == null) {
                    FrameLayout container = findViewById(R.id.FLXServerDisplay);
                    magnifierView = new MagnifierView(this);
                    magnifierView.setZoomButtonCallback(value -> {
                        renderer.setMagnifierZoom(Mathf.clamp(renderer.getMagnifierZoom() + value, 1.0f, 3.0f));
                        magnifierView.setZoomValue(renderer.getMagnifierZoom());
                    });
                    magnifierView.setZoomValue(renderer.getMagnifierZoom());
                    magnifierView.setHideButtonCallback(() -> {
                        container.removeView(magnifierView);
                        magnifierView = null;
                    });
                    container.addView(magnifierView);
                }
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_screen_effects:
                Log.d("ScreenEffectDialog", "Initializing ScreenEffectDialog");
                ScreenEffectDialog screenEffectDialog = new ScreenEffectDialog(this);
                screenEffectDialog.setOnConfirmCallback(() -> {
                    Log.d("ScreenEffectDialog", "Confirm callback triggered. About to apply effects.");
                    GLRenderer currentRenderer = xServerView.getRenderer();
                    ColorEffect colorEffect = (ColorEffect) currentRenderer.getEffectComposer().getEffect(ColorEffect.class);
                    FXAAEffect fxaaEffect = (FXAAEffect) currentRenderer.getEffectComposer().getEffect(FXAAEffect.class);
                    CRTEffect crtEffect = (CRTEffect) currentRenderer.getEffectComposer().getEffect(CRTEffect.class);
                    ToonEffect toonEffect = (ToonEffect) currentRenderer.getEffectComposer().getEffect(ToonEffect.class);
                    NTSCCombinedEffect ntscEffect = (NTSCCombinedEffect) currentRenderer.getEffectComposer().getEffect(NTSCCombinedEffect.class);

                    // Check if effects are null before applying
                    Log.d("ScreenEffectDialog", "ColorEffect: " + (colorEffect != null));
                    Log.d("ScreenEffectDialog", "FXAAEffect: " + (fxaaEffect != null));
                    Log.d("ScreenEffectDialog", "CRTEffect: " + (crtEffect != null));
                    Log.d("ScreenEffectDialog", "ToonEffect: " + (toonEffect != null));
                    Log.d("ScreenEffectDialog", "NTSCCombinedEffect: " + (ntscEffect != null));

                    Log.d("ScreenEffectDialog", "Calling applyEffects()");
                    screenEffectDialog.applyEffects(colorEffect, currentRenderer, fxaaEffect, crtEffect, toonEffect, ntscEffect);
                    Log.d("ScreenEffectDialog", "applyEffects() called.");
                });
                Log.d("ScreenEffectDialog", "Showing ScreenEffectDialog");
                screenEffectDialog.show();
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_logs:
                debugDialog.show();
                drawerLayout.closeDrawers();
                break;
            case R.id.main_menu_touchpad_help:
                showTouchpadHelpDialog();
                break;
            case R.id.main_menu_terminal:  // New case for TerminalActivity
                openTerminal();
                return true;
            case R.id.main_menu_winetricks:
                if (winetricksFloatingView == null) {
                    FrameLayout frameLayout = findViewById(R.id.FLXServerDisplay);
                    winetricksFloatingView = new WinetricksFloatingView(this);
                    winetricksFloatingView.setWinetricksListener(new WinetricksFloatingView.WinetricksListener() {
                        @Override
                        public void onWinetricksStableClick(String verb, TextView outputView) {
                            if (!verb.isEmpty()) {
                                runWinetricksWithVerb(container, contentsManager, verb, outputView); // Use container here
                            } else {
                                Toast.makeText(XServerDisplayActivity.this, "Please enter a Winetricks verb", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onWinetricksLatestClick(String verb, TextView outputView) {
                            if (!verb.isEmpty()) {
                                runWinetricksLatestWithVerb(container, contentsManager, verb, outputView); // Use container here
                            } else {
                                Toast.makeText(XServerDisplayActivity.this, "Please enter a Winetricks verb", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onOpenWinetricksFolder(TextView outputView) {
                            runWinetricksFolder(container, contentsManager, outputView); // Use container here
                        }

                        @Override
                        public void onToggleTransparency(View floatingView) {
                            if (floatingView.getAlpha() < 1.0f) {
                                floatingView.setAlpha(1.0f);
                            } else {
                                floatingView.setAlpha(0.5f);
                            }
                        }

                        @Override
                        public void onRestartWineserverClick(TextView outputView) {
                            // NEW
                            try {
                                environment.setWinetricksRunning(true);
                                // Determine whether to use Glibc or Bionic launcher based on preference
                                if (bionicLauncher != null) {
                                    bionicLauncher.restartWineServer();
                                } else {
                                    runOnUiThread(() -> {
                                        outputView.append("No valid launcher found; cannot restart Wineserver.\n");
                                    });
                                    return; // Exit the method early if no valid launcher is found
                                }

                                // If the environment needs frequent re-initialization
                                setupXEnvironment();

                                // Confirm to the user in logs
                                runOnUiThread(() -> {
                                    outputView.append("Wineserver restarted.\n");
                                });

                            } catch (Exception e) {
                            }
                            environment.setWinetricksRunning(false);
                        }

                    });
                    frameLayout.addView(winetricksFloatingView);
                } else {
                    winetricksFloatingView.setVisibility(View.VISIBLE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.main_menu_exit:
                exit();
                break;
        }
        return true;
    }

    private void openTerminal() {
        Intent intent = new Intent(this, TerminalActivity.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        boolean cursorLock = preferences.getBoolean("cursor_lock", false);

        if (hasFocus && !pointerCaptureRequested && cursorLock) {
            // Ensure TouchpadView and other relevant views are focused
            touchpadView.setFocusable(View.FOCUSABLE);
            touchpadView.setFocusableInTouchMode(true);
            touchpadView.requestFocus();
            touchpadView.requestPointerCapture();

            touchpadView.setOnCapturedPointerListener(new View.OnCapturedPointerListener() {
                @Override
                public boolean onCapturedPointer(View view, MotionEvent event) {
                    handleCapturedPointer(event);
                    return true;
                }
            });

            pointerCaptureRequested = true; // Ensure this is only called once

        } else if (!hasFocus) {
            if (touchpadView != null) {
                touchpadView.releasePointerCapture();
                touchpadView.setOnCapturedPointerListener(null);
            }
        }
    }

    private void setupWineSystemFiles() {
        String appVersion = String.valueOf(AppUtils.getVersionCode(this));
        String imgVersion = String.valueOf(imageFs.getVersion());
        boolean containerDataChanged = false;

        if (!container.getExtra("appVersion").equals(appVersion) || !container.getExtra("imgVersion").equals(imgVersion)) {
            applyGeneralPatches(container);
            container.putExtra("appVersion", appVersion);
            container.putExtra("imgVersion", imgVersion);
            containerDataChanged = true;
        }

        String dxwrapper = this.dxwrapper;
        if (dxwrapper.equals("dxvk"))
            dxwrapper = "dxvk-"+dxwrapperConfig.get("version");
        else if (dxwrapper.equals("vkd3d"))
            dxwrapper = "vkd3d-"+dxwrapperConfig.get("vkd3dVersion");

        if (!dxwrapper.equals(container.getExtra("dxwrapper"))) {
            extractDXWrapperFiles(dxwrapper);
            container.putExtra("dxwrapper", dxwrapper);
            containerDataChanged = true;
        }

        String ddrawrapper = this.ddrawrapper;

        if (!ddrawrapper.equals(container.getExtra("ddrawrapper"))) {
            extractDDrawrapperFiles(ddrawrapper);
            container.putExtra("ddrawrapper", ddrawrapper);
            containerDataChanged = true;
        }

        if (ddrawrapper.equals("cnc-ddraw")) envVars.put("CNC_DDRAW_CONFIG_FILE", "C:\\windows\\syswow64\\ddraw.ini");

        String wincomponents = shortcut != null ? shortcut.getExtra("wincomponents", container.getWinComponents()) : container.getWinComponents();
        if (!wincomponents.equals(container.getExtra("wincomponents"))) {
            extractWinComponentFiles();
            container.putExtra("wincomponents", wincomponents);
            containerDataChanged = true;
        }

        String desktopTheme = container.getDesktopTheme();
        if (!(desktopTheme+","+xServer.screenInfo).equals(container.getExtra("desktopTheme"))) {
            WineThemeManager.apply(this, new WineThemeManager.ThemeInfo(desktopTheme), xServer.screenInfo);
            container.putExtra("desktopTheme", desktopTheme+","+xServer.screenInfo);
            containerDataChanged = true;
        }

        WineStartMenuCreator.create(this, container);
        WineUtils.createDosdevicesSymlinks(container);

        if (shortcut != null)
            startupSelection = shortcut.getExtra("startupSelection", String.valueOf(container.getStartupSelection()));
        else
            startupSelection = String.valueOf(container.getStartupSelection());

        if (!startupSelection.equals(container.getExtra("startupSelection"))) {
            WineUtils.changeServicesStatus(container, Byte.parseByte(startupSelection) != Container.STARTUP_SELECTION_NORMAL);
            container.putExtra("startupSelection", startupSelection);
            containerDataChanged = true;
        }

        if (containerDataChanged) container.saveData();
    }

    private void setupXEnvironment() throws PackageManager.NameNotFoundException {
        // Optional: Example logging or debugging code
        // try {
        //     // Execute busybox whoami to log the UID
        //     Process whoamiProcess = new ProcessBuilder("/data/data/com.winlator/files/imagefs/usr/bin/busybox", "whoami").start();
        //     BufferedReader reader = new BufferedReader(new InputStreamReader(whoamiProcess.getInputStream()));
        //     String uid = reader.readLine();
        //     whoamiProcess.waitFor();
        //
        //     // Log the UID to understand what user is being used
        //     Log.d("Winetricks", "Current UID executing Wine: " + (uid != null ? uid : "unknown"));
        // } catch (Exception e) {
        //     Log.e("Winetricks", "Error executing busybox whoami: " + e.getMessage(), e);
        // }

        // ... (Other optional debugging code omitted) ...

        // Set environment variables
        envVars.put("LC_ALL", lc_all);
        envVars.put("MESA_DEBUG", "silent");
        envVars.put("MESA_NO_ERROR", "1");
        envVars.put("WINEPREFIX", imageFs.wineprefix);
        Log.d("Winetricks", "WINEPREFIX: " + imageFs.wineprefix);

        boolean enableWineDebug = preferences.getBoolean("enable_wine_debug", false);
        String wineDebugChannels = preferences.getString("wine_debug_channels", SettingsFragment.DEFAULT_WINE_DEBUG_CHANNELS);
        envVars.put("WINEDEBUG", enableWineDebug && !wineDebugChannels.isEmpty()
                ? "+" + wineDebugChannels.replace(",", ",+")
                : "-all"
        );

        // Clear any temporary directory
        String rootPath = imageFs.getRootDir().getPath();
        FileUtils.clear(imageFs.getTmpDir());


        // Create the appropriate launcher based on the container type
        GuestProgramLauncherComponent guestProgramLauncherComponent;

        bionicLauncher = new BionicProgramLauncherComponent(
                contentsManager,
                contentsManager.getProfileByEntryName(container.getWineVersion()),
                shortcut
        );
        guestProgramLauncherComponent = bionicLauncher;
        glibcLauncher = null; // We're not using glibc in this case

        // Additional container checks and environment configuration
        if (container != null) {
            if (Byte.parseByte(startupSelection) == Container.STARTUP_SELECTION_AGGRESSIVE) {
                winHandler.killProcess("services.exe");
            }
            bionicLauncher.setContainer(this.container);
            bionicLauncher.setWineInfo(this.wineInfo);
            boolean wow64Mode = container.isWoW64Mode();
            // Construct the guest executable command
            String guestExecutable = "wine explorer /desktop=shell," + xServer.screenInfo + " " + getWineStartCommand();
            // (Alternatively: "wine wineboot -u" or anything else you want)

            Log.d("Winetricks", "Guest executable: " + guestExecutable);

            // Set up the guest program parameters
            guestProgramLauncherComponent.setWoW64Mode(wow64Mode);
            guestProgramLauncherComponent.setGuestExecutable(guestExecutable);

            // Merge in container’s environment variables
            envVars.putAll(container.getEnvVars());
            
            // Merge in shortcut environment variables if present
            if (shortcut != null) envVars.putAll(shortcut.getExtra("envVars"));

            // If WINEESYNC is not defined, default to "1"
            if (!envVars.has("WINEESYNC")) {
                envVars.put("WINEESYNC", "1");
            }

            // Bind any drive paths the container defines
            ArrayList<String> bindingPaths = new ArrayList<>();
            for (String[] drive : container.drivesIterator()) {
                bindingPaths.add(drive[1]);
            }
            guestProgramLauncherComponent.setBindingPaths(bindingPaths.toArray(new String[0]));

            // Box86/64 presets from container or shortcut
            guestProgramLauncherComponent.setBox64Preset(
                    shortcut != null
                            ? shortcut.getExtra("box64Preset", container.getBox64Preset())
                            : container.getBox64Preset()
            );
        }

        // Merge overrideEnvVars if present
        if (overrideEnvVars != null) {
            envVars.putAll(overrideEnvVars);
            overrideEnvVars.clear(); // Clear overrideEnvVars as per smali logic
        }

        // Create our overall XEnvironment with various components
        environment = new XEnvironment(this, imageFs);
        environment.addComponent(
                new SysVSharedMemoryComponent(
                        xServer,
                        UnixSocketConfig.createSocket(rootPath, UnixSocketConfig.SYSVSHM_SERVER_PATH)
                )
        );
        environment.addComponent(
                new XServerComponent(
                        xServer,
                        UnixSocketConfig.createSocket(rootPath, UnixSocketConfig.XSERVER_PATH)
                )
        );


        environment.addComponent(new NetworkInfoUpdateComponent());

        // Audio driver logic
        if (audioDriver.equals("alsa")) {
            envVars.put("ANDROID_ALSA_SERVER", rootPath + UnixSocketConfig.ALSA_SERVER_PATH);
            envVars.put("ANDROID_ASERVER_USE_SHM", "true");
            environment.addComponent(
                    new ALSAServerComponent(
                            UnixSocketConfig.createSocket(rootPath, UnixSocketConfig.ALSA_SERVER_PATH)
                    )
            );
        } else if (audioDriver.equals("pulseaudio")) {
            envVars.put("PULSE_SERVER", rootPath + UnixSocketConfig.PULSE_SERVER_PATH);
            environment.addComponent(
                    new PulseAudioComponent(
                            UnixSocketConfig.createSocket(rootPath, UnixSocketConfig.PULSE_SERVER_PATH)
                    )
            );
        }

        // RC (box86_64rc) file handling
        RCManager manager = new RCManager(this);
        manager.loadRCFiles();
        int rcfileId = shortcut == null
                ? container.getRCFileId()
                : Integer.parseInt(shortcut.getExtra("rcfileId", String.valueOf(container.getRCFileId())));
        RCFile rcfile = manager.getRcfile(rcfileId);

        File file = new File(container.getRootDir(), ".box64rc");
        String str = rcfile == null ? "" : rcfile.generateBox86_64rc();
        FileUtils.writeString(file, str);

        // Let Box64 inside Wine see this config
        envVars.put("BOX64_RCFILE", file.getAbsolutePath());

        // Pass final envVars to the launcher
        guestProgramLauncherComponent.setEnvVars(envVars);
        guestProgramLauncherComponent.setTerminationCallback((status) -> exit());

        // Add the launcher to our environment
        environment.addComponent(guestProgramLauncherComponent);

        // If we need to auto-generate a wineprefix
        if (isGenerateWineprefix()) {
            generateWineprefix();
        }

        // Generate fexcore per app settings
        FEXCoreManager.createAppConfigFiles(this);

        // Start all environment components (XServer, Audio, etc.)
        environment.startEnvironmentComponents();

        // (Optionally) run Winetricks after setup, if you wish
        // runWinetricksAfterSetup();

        // Start the WinHandler
        winHandler.start();

        if (wineRequestHandler != null) wineRequestHandler.start();

        // Clear envVars if needed
        // envVars.clear();

        // Reset dxwrapper config
        dxwrapperConfig = null;
        
    }



    private void createWineWrappers(Container container, ContentsManager contentsManager) {
        String wineBinPath;
        String wineLibPath;
        String box64Path = imageFs.getRootDir().getPath() + "/usr/local/bin/box64";
        String usrLocalBin = imageFs.getRootDir().getPath() + "/usr/local/bin";

        // Determine if the container is using a contents profile Wine version
        ContentProfile profile = contentsManager.getProfileByEntryName(container.getWineVersion());
        if (profile != null && profile.type == ContentProfile.ContentType.CONTENT_TYPE_WINE) {
            File profileInstallDir = contentsManager.getInstallDir(this, profile);
            wineBinPath = profileInstallDir.getPath() + "/" + profile.wineBinPath;
            wineLibPath = profileInstallDir.getPath() + "/" + profile.wineLibPath;
        } else {
            wineBinPath = imageFs.getWinePath() + "/bin";
            wineLibPath = imageFs.getWinePath() + "/lib/wine";
        }

        // Fetch stored environment variables
        Map<String, String> envVars = EnvironmentManager.getEnvVars();

        // Build environment export section dynamically
        StringBuilder dynamicEnvExports = new StringBuilder("#!" + imageFs.getRootDir() + "/usr/bin/dash\n");
        for (Map.Entry<String, String> entry : envVars.entrySet()) {
            dynamicEnvExports.append("export ").append(entry.getKey()).append("=\"")
                    .append(entry.getValue().replace("\"", "\\\"")).append("\"\n");
        }

        // Define the wine and wine64 wrappers to open explorer.exe with the desktop shell
        String wineExecContent = dynamicEnvExports.toString() +
                "exec \"" + box64Path + "\" \"" + wineBinPath + "/wine\" explorer.exe /desktop=shell," + xServer.screenInfo + " \"$@\"";
        createWrapperScript(usrLocalBin + "/wine", wineExecContent);
        createWrapperScript(usrLocalBin + "/wine64", wineExecContent);

        // Define the wineserver wrapper with a different exec command
        String wineserverContent = dynamicEnvExports.toString() +
                "exec \"" + box64Path + "\" \"" + wineBinPath + "/wineserver\" \"$@\"";
        createWrapperScript(usrLocalBin + "/wineserver", wineserverContent);
    }

    private void createWrapperScript(String path, String content) {
        File scriptFile = new File(path);
        FileUtils.writeString(scriptFile, content);
        scriptFile.setExecutable(true);
    }


    private static final int MAX_LOG_LINES = 1000;
    private static final int BATCH_SIZE = 10;

    private void runWinetricksWithVerb(Container container,
                                       ContentsManager contentsManager,
                                       String verb,
                                       TextView outputView) {

        // 1. Tell the environment that Winetricks is about to run
        environment.setWinetricksRunning(true);

        // Example: create wrappers, etc.
        createWineWrappers(container, contentsManager);

        Map<String, String> envVars = EnvironmentManager.getEnvVars();
        String usrLocalBin = imageFs.getRootDir().getPath() + "/usr/local/bin";
        String wineBinPath = imageFs.getWinePath() + "/bin";
        String defaultPath = usrLocalBin + ":" + wineBinPath + ":" + imageFs.getRootDir().getPath() + "/usr/bin";
        envVars.put("PATH", defaultPath);

        File winetricksFile = new File(imageFs.getRootDir(), "/usr/bin/winetricks");
        if (!winetricksFile.exists()) {
            Log.e("Winetricks", "winetricks script not found at " + winetricksFile.getAbsolutePath());
            // IMPORTANT: re-enable the callback if we fail early
            environment.setWinetricksRunning(false);
            return;
        }
        winetricksFile.setExecutable(true);

        String[] command = {
                winetricksFile.getAbsolutePath(),
                "--force",
                verb
        };

        Executor executor = Executors.newSingleThreadExecutor();

        final Process[] process = {null};

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.directory(new File(imageFs.home_path));
                    Map<String, String> environmentVars = processBuilder.environment();
                    for (Map.Entry<String, String> entry : envVars.entrySet()) {
                        environmentVars.put(entry.getKey(), entry.getValue());
                    }
                    processBuilder.redirectErrorStream(true);
                    process[0] = processBuilder.start();
                    runOnUiThread(() -> outputView.setText(""));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process[0].getInputStream()));
                    String outputLine = null;
                    while (((outputLine = reader.readLine()) != null)) {
                        if (outputLine != null) {
                            String finalOutputLine = outputLine;
                            runOnUiThread(() -> outputView.append(finalOutputLine + "\n"));
                        }
                    }
                    int exitCode = process[0].waitFor();
                    runOnUiThread(() -> outputView.append("Winetricks exited with code " + exitCode + "\n"));

                } catch (Exception e) {
                    String msg = "Error executing winetricks: " + e.getMessage();
                    runOnUiThread(() -> outputView.setText(msg));
                } finally {
                    // 2. Once Winetricks finishes or fails, kill eventual stale processes
                    environment.setWinetricksRunning(false);
                }
            }
        });
    }

    private void runWinetricksLatestWithVerb(Container container,
                                             ContentsManager contentsManager,
                                             String verb,
                                             TextView outputView) {

        // 1. Mark Winetricks as running
        environment.setWinetricksRunning(true);

        // 2. Create wrappers, etc. (same as normal)
        createWineWrappers(container, contentsManager);

        Map<String, String> envVars = EnvironmentManager.getEnvVars();
        String usrLocalBin = imageFs.getRootDir().getPath() + "/usr/local/bin";
        String wineBinPath = imageFs.getWinePath() + "/bin";
        String defaultPath = usrLocalBin + ":" + wineBinPath + ":" + imageFs.getRootDir().getPath() + "/usr/bin";
        envVars.put("PATH", defaultPath);

        // 3. **Use winetricks.latest** instead of winetricks
        File winetricksFile = new File(imageFs.getRootDir(), "/usr/bin/winetricks.latest");
        if (!winetricksFile.exists()) {
            Log.e("WinetricksLatest", "winetricks.latest script not found at " + winetricksFile.getAbsolutePath());
            // Re-enable callback if we fail early
            environment.setWinetricksRunning(false);
            return;
        }
        winetricksFile.setExecutable(true);

        // 4. Build the command array for the script + verb
        String[] command = {
                winetricksFile.getAbsolutePath(),
                "--force",
                verb
        };

        new Thread(() -> {
            Process process = null;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.directory(new File(imageFs.home_path));

                Map<String, String> environmentVars = processBuilder.environment();
                for (Map.Entry<String, String> entry : envVars.entrySet()) {
                    environmentVars.put(entry.getKey(), entry.getValue());
                }

                process = processBuilder.start();
                process.getOutputStream().close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                appendBufferedLog(reader, outputView, false);
                appendBufferedLog(errorReader, outputView, true);

                int exitCode = process.waitFor();
                final int finalExitCode = exitCode;
                runOnUiThread(() -> outputView.append("Winetricks Latest exited with code " + finalExitCode + "\n"));

            } catch (Exception e) {
                String msg = "Error executing winetricks.latest: " + e.getMessage();
                runOnUiThread(() -> outputView.setText(msg));
            } finally {
                // Re-enable normal callback
                environment.setWinetricksRunning(false);
            }
        }).start();
    }

    private void appendBufferedLog(BufferedReader reader, TextView outputView, boolean isError) throws IOException {
        ArrayDeque<String> logBuffer = new ArrayDeque<>(MAX_LOG_LINES);
        StringBuilder batchBuffer = new StringBuilder();
        String line;
        int batchCount = 0;

        while ((line = reader.readLine()) != null) {
            String finalLine = (isError ? "Error: " : "") + line;

            if (logBuffer.size() >= MAX_LOG_LINES) {
                logBuffer.pollFirst(); // Remove the oldest line
            }
            logBuffer.addLast(finalLine);

            batchBuffer.append(finalLine).append("\n");
            batchCount++;

            if (batchCount >= BATCH_SIZE) {
                String logContent = String.join("\n", logBuffer);
                runOnUiThread(() -> outputView.setText(logContent));
                batchBuffer.setLength(0); // Clear batch buffer
                batchCount = 0;
            }
        }

        // Final update if there are remaining lines
        if (batchBuffer.length() > 0) {
            String logContent = String.join("\n", logBuffer);
            runOnUiThread(() -> outputView.setText(logContent));
        }
    }

    private void runWinetricksFolder(Container container, ContentsManager contentsManager, TextView outputView) {
        // The path to where you'd like to store your dynamic script
        String scriptPath = imageFs.getRootDir() + "/usr/bin/winetricksfolder";

        // 1. Generate (or overwrite) the script
        createWinetricksFolderScript(container, contentsManager, scriptPath);

        // 2. Verify it got created
        File winetricksFolderFile = new File(scriptPath);
        if (!winetricksFolderFile.exists()) {
            Log.e("WinetricksFolder", "winetricksfolder script not found after creation.");
            outputView.setText("Error: winetricksfolder script not found.");
            return;
        }
        winetricksFolderFile.setExecutable(true);

        // 3. Execute the newly created script
        String[] command = { winetricksFolderFile.getAbsolutePath() };

        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Map<String, String> environment = processBuilder.environment();

                // Optionally set additional environment variables if needed
                // environment.put("HOME", imageFs.home_path);
                // environment.put("DISPLAY", ":0");
                // etc.

                // Start process
                Process process = processBuilder.start();
                process.getOutputStream().close();

                // Capture output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                while ((line = errorReader.readLine()) != null) {
                    output.append("Error: ").append(line).append("\n");
                }

                int exitCode = process.waitFor();
                output.append("winetricksfolder script exited with code ").append(exitCode).append("\n");

                runOnUiThread(() -> outputView.setText(output.toString()));

            } catch (Exception e) {
                runOnUiThread(() -> outputView.setText("Error executing winetricksfolder: " + e.getMessage()));
            }
        }).start();
    }


    private void createWinetricksFolderScript(
            Container container,
            ContentsManager contentsManager,
            String scriptPath
    ) {
        // 1. Figure out which Wine bin path to use based on the container/profile
        ContentProfile profile = contentsManager.getProfileByEntryName(container.getWineVersion());
        String wineBinPath;
        if (profile != null && profile.type == ContentProfile.ContentType.CONTENT_TYPE_WINE) {
            File profileInstallDir = contentsManager.getInstallDir(this, profile);
            wineBinPath = profileInstallDir.getPath() + "/" + profile.wineBinPath;
        } else {
            wineBinPath = imageFs.getWinePath() + "/bin";
        }

        // 2. Construct environment-variable exports similarly to createWineWrappers()
        //    (fetch the env vars from EnvironmentManager)
        Map<String, String> envVars = EnvironmentManager.getEnvVars();
        StringBuilder dynamicEnvExports = new StringBuilder("#!" + imageFs.getRootDir().getPath() + "/usr/bin/dash\n");

        for (Map.Entry<String, String> entry : envVars.entrySet()) {
            // Properly escape quotes
            String escapedValue = entry.getValue().replace("\"", "\\\"");
            dynamicEnvExports
                    .append("export ")
                    .append(entry.getKey())
                    .append("=\"")
                    .append(escapedValue)
                    .append("\"\n");
        }

        // 3. Add your final “exec” line
        //    For example, run wine explorer.exe /desktop=shell wfm ...
        //    Also note if you need box64 or not—depends on your environment.
        String box64Path = imageFs.getRootDir().getPath() + "/usr/local/bin/box64";

        dynamicEnvExports.append("exec \"")
                .append(box64Path).append("\" \"")
                .append(wineBinPath).append("/wine\" ")
                .append("explorer.exe /desktop=shell wfm ")
                .append("\"" + imageFs.getRootDir().getPath() +  "/home/xuser/.cache/winetricks\"")
                .append("\n");

        // 4. Write the script to disk
        File scriptFile = new File(scriptPath);
        FileUtils.writeString(scriptFile, dynamicEnvExports.toString());
        scriptFile.setExecutable(true);
    }


    private void setupUI() {
        FrameLayout rootView = findViewById(R.id.FLXServerDisplay);
        xServerView = new XServerView(this, xServer);
        final GLRenderer renderer = xServerView.getRenderer();
        renderer.setCursorVisible(false);

        if (shortcut != null) {
            if (shortcut.getExtra("forceFullscreen", "0").equals("1")) renderer.setForceFullscreenWMClass(shortcut.wmClass);
            renderer.setUnviewableWMClasses("explorer.exe");
        }

        xServer.setRenderer(renderer);
        rootView.addView(xServerView);

        globalCursorSpeed = preferences.getFloat("cursor_speed", 1.0f);
        touchpadView = new TouchpadView(this, xServer, timeoutHandler, hideControlsRunnable);
        touchpadView.setSensitivity(globalCursorSpeed);
        touchpadView.setFourFingersTapCallback(() -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(GravityCompat.START);
        });
        rootView.addView(touchpadView);

        inputControlsView = new InputControlsView(this, timeoutHandler, hideControlsRunnable);
        inputControlsView.setOverlayOpacity(preferences.getFloat("overlay_opacity", InputControlsView.DEFAULT_OVERLAY_OPACITY));
        inputControlsView.setTouchpadView(touchpadView);
        inputControlsView.setXServer(xServer);
        inputControlsView.setVisibility(View.GONE);
        rootView.addView(inputControlsView);


        startTouchscreenTimeout();

        // Inside onCreate(), after initializing controls
        boolean isTimeoutEnabled = preferences.getBoolean("touchscreen_timeout_enabled", false);
        if (isTimeoutEnabled) {
            startTouchscreenTimeout();
        }

        if (container != null && container.isShowFPS()) {
            frameRating = new FrameRating(this, container);
            frameRating.setVisibility(View.GONE);
            rootView.addView(frameRating);
        }

        // Get the fullscreen stretched extra from the shortcut if available
        String shortcutFullscreenStretched = shortcut != null ? shortcut.getExtra("fullscreenStretched") : null;

        // Proceed based on container and shortcut settings
        boolean shouldStretch = false;

        if (shortcut != null && shortcutFullscreenStretched != null) {
            // Shortcut exists and has a valid setting
            shouldStretch = shortcutFullscreenStretched.equals("1");
        } else if (container != null && container.isFullscreenStretched()) {
            // No shortcut or shortcut doesn't override, use the container's setting
            shouldStretch = true;
        }

        if (shouldStretch) {
            // Toggle fullscreen mode based on the final decision
            renderer.toggleFullscreen();
            touchpadView.toggleFullscreen();
        }

        if (shortcut != null) {
            String controlsProfile = shortcut.getExtra("controlsProfile");
            if (!controlsProfile.isEmpty()) {
                ControlsProfile profile = inputControlsManager.getProfile(Integer.parseInt(controlsProfile));
                if (profile != null) showInputControls(profile);
            }

            String simTouchScreen = shortcut.getExtra("simTouchScreen");
            touchpadView.setSimTouchScreen(simTouchScreen.equals("1"));
        }

        AppUtils.observeSoftKeyboardVisibility(drawerLayout, renderer::setScreenOffsetYRelativeToCursor);
    }



    private ActivityResultLauncher<Intent> controlsEitorActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (editInputControlsCallback != null) {
                    editInputControlsCallback.run();
                    editInputControlsCallback = null;
                }
            }
    );

    private String parseShortcutNameFromDesktopFile(File desktopFile) {
        String shortcutName = "";
        if (desktopFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(desktopFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Name=")) {
                        shortcutName = line.split("=")[1].trim();
                        break;
                    }
                }
            } catch (IOException e) {
                Log.e("XServerDisplayActivity", "Error reading shortcut name from .desktop file", e);
            }
        }
        return shortcutName;
    }

    private void setTextColorForDialog(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                // If the child is a ViewGroup, recursively apply the color
                setTextColorForDialog((ViewGroup) child, color);
            } else if (child instanceof TextView) {
                // If the child is a TextView, set its text color
                ((TextView) child).setTextColor(color);
            }
        }
    }

    private void showInputControlsDialog() {
        final ContentDialog dialog = new ContentDialog(this, R.layout.input_controls_dialog);
        dialog.setTitle(R.string.input_controls);
        dialog.setIcon(R.drawable.icon_input_controls);

        final Spinner sProfile = dialog.findViewById(R.id.SProfile);

        dialog.getWindow().setBackgroundDrawableResource(isDarkMode ? R.drawable.content_dialog_background_dark : R.drawable.content_dialog_background);
        sProfile.setPopupBackgroundResource(isDarkMode ? R.drawable.content_dialog_background_dark : R.drawable.content_dialog_background);

        // Set text color for all TextViews in the dialog to white or black based on dark mode
        int textColor = ContextCompat.getColor(this, isDarkMode ? R.color.white : R.color.black);
        ViewGroup dialogViewGroup = (ViewGroup) dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        setTextColorForDialog(dialogViewGroup, textColor);

        Runnable loadProfileSpinner = () -> {
            ArrayList<ControlsProfile> profiles = inputControlsManager.getProfiles(true);
            ArrayList<String> profileItems = new ArrayList<>();
            int selectedPosition = 0;
            profileItems.add("-- "+getString(R.string.disabled)+" --");
            for (int i = 0; i < profiles.size(); i++) {
                ControlsProfile profile = profiles.get(i);
                if (inputControlsView.getProfile() != null && profile.id == inputControlsView.getProfile().id)
                    selectedPosition = i + 1;
                profileItems.add(profile.getName());
            }

            sProfile.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, profileItems));
            sProfile.setSelection(selectedPosition);
        };
        loadProfileSpinner.run();

        final CheckBox cbRelativeMouseMovement = dialog.findViewById(R.id.CBRelativeMouseMovement);
        cbRelativeMouseMovement.setChecked(xServer.isRelativeMouseMovement());

        final CheckBox cbSimTouchScreen = dialog.findViewById(R.id.CBSimulateTouchScreen);
        cbSimTouchScreen.setChecked(touchpadView.isSimTouchScreen());

        final CheckBox cbShowTouchscreenControls = dialog.findViewById(R.id.CBShowTouchscreenControls);
        cbShowTouchscreenControls.setChecked(inputControlsView.isShowTouchscreenControls());

        final CheckBox cbEnableTimeout = dialog.findViewById(R.id.CBEnableTimeout);
        cbEnableTimeout.setChecked(preferences.getBoolean("touchscreen_timeout_enabled", false));

        final CheckBox cbEnableHaptics = dialog.findViewById(R.id.CBEnableHaptics);
        cbEnableHaptics.setChecked(preferences.getBoolean("touchscreen_haptics_enabled", false));

        final Runnable updateProfile = () -> {
            int position = sProfile.getSelectedItemPosition();
            if (position > 0) {
                showInputControls(inputControlsManager.getProfiles().get(position - 1));
            }
            else hideInputControls();
        };

        dialog.findViewById(R.id.BTSettings).setOnClickListener((v) -> {
            int position = sProfile.getSelectedItemPosition();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("edit_input_controls", true);
            intent.putExtra("selected_profile_id", position > 0 ? inputControlsManager.getProfiles().get(position - 1).id : 0);
            editInputControlsCallback = () -> {
                hideInputControls();
                inputControlsManager.loadProfiles(true);
                loadProfileSpinner.run();
                updateProfile.run();
            };
            controlsEitorActivityResultLauncher.launch(intent);
        });

        dialog.setOnConfirmCallback(() -> {
            xServer.setRelativeMouseMovement(cbRelativeMouseMovement.isChecked());
            inputControlsView.setShowTouchscreenControls(cbShowTouchscreenControls.isChecked());
            boolean isTimeoutEnabled = cbEnableTimeout.isChecked();
            boolean isHapticsEnabled = cbEnableHaptics.isChecked();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("touchscreen_timeout_enabled", isTimeoutEnabled);
            editor.putBoolean("touchscreen_haptics_enabled", isHapticsEnabled);
            editor.apply();

            if (isTimeoutEnabled) {
                startTouchscreenTimeout(); // Start the timeout functionality if enabled
            } else {
                touchpadView.setOnTouchListener(null); // Disable the listener if timeout is disabled
            }
            int position = sProfile.getSelectedItemPosition();
            if (position > 0) {
                showInputControls(inputControlsManager.getProfiles().get(position - 1));
            }
            else hideInputControls();
            touchpadView.setSimTouchScreen(cbSimTouchScreen.isChecked());
            updateProfile.run();
        });

        dialog.setOnCancelCallback(updateProfile::run);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void simulateConfirmInputControlsDialog() {
        // Simulate setting the relative mouse movement and touchscreen controls from preferences
        boolean isRelativeMouseMovement = preferences.getBoolean("relative_mouse_movement_enabled", false);
        boolean isForceMouseControl = preferences.getBoolean("force_mouse_control_enabled", false);
        xServer.setRelativeMouseMovement(isRelativeMouseMovement);
        xServer.setForceMouseControl(isForceMouseControl);

        boolean isShowTouchscreenControls = preferences.getBoolean("show_touchscreen_controls_enabled", false); // default is false (hidden)
        inputControlsView.setShowTouchscreenControls(isShowTouchscreenControls);

        boolean isTimeoutEnabled = preferences.getBoolean("touchscreen_timeout_enabled", false);
        boolean isHapticsEnabled = preferences.getBoolean("touchscreen_haptics_enabled", false);

        // Apply these settings as if the user confirmed the dialog
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("touchscreen_timeout_enabled", isTimeoutEnabled);
        editor.putBoolean("touchscreen_haptics_enabled", isHapticsEnabled);
        editor.apply();

        // If no profile is selected, hide the controls
        int selectedProfileIndex = preferences.getInt("selected_profile_index", -1); // Default to -1 for no profile

        if (selectedProfileIndex >= 0 && selectedProfileIndex < inputControlsManager.getProfiles().size()) {
            // A profile is selected, show the controls
            ControlsProfile profile = inputControlsManager.getProfiles().get(selectedProfileIndex);
            showInputControls(profile);
        } else {
            // No profile selected, ensure the controls are hidden
            hideInputControls();
        }

        // Timeout logic should only apply if the controls are visible
        if (isTimeoutEnabled && inputControlsView.getVisibility() == View.VISIBLE) {
            startTouchscreenTimeout(); // Start timeout if enabled and controls are visible
        } else {
            touchpadView.setOnTouchListener(null); // Disable the timeout listener if not needed
        }

        Log.d("XServerDisplayActivity", "Input controls simulated confirmation executed.");
    }

    private void startTouchscreenTimeout() {
        boolean isTimeoutEnabled = preferences.getBoolean("touchscreen_timeout_enabled", false);

        if (isTimeoutEnabled) {
            // Show controls initially and set up touch event listeners
            inputControlsView.setVisibility(View.VISIBLE);
            Log.d("XServerDisplayActivity", "Timeout is enabled, setting up timeout logic.");

            // Attach the OnTouchListener to reset the timeout on touch events
            touchpadView.setOnTouchListener((v, event) -> {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    // Reset the timeout on any touch event
                    //Log.d("XServerDisplayActivity", "Touch detected, resetting timeout.");

                    // Keep the controls visible
                    inputControlsView.setVisibility(View.VISIBLE);

                    // Remove any pending hide callbacks and reset the timeout
                    timeoutHandler.removeCallbacks(hideControlsRunnable);
                    timeoutHandler.postDelayed(hideControlsRunnable, 5000); // Reset timeout
                }

                return false; // Allow the touch event to propagate
            });

            // Reset the timeout when the controls are initially displayed
            timeoutHandler.removeCallbacks(hideControlsRunnable);
            timeoutHandler.postDelayed(hideControlsRunnable, 5000); // Hide after 5 seconds of inactivity
        } else {
            // If timeout is disabled, keep the controls always visible
            Log.d("XServerDisplayActivity", "Timeout is disabled, controls will stay visible.");

            inputControlsView.setVisibility(View.VISIBLE); // Ensure controls are visible
            timeoutHandler.removeCallbacks(hideControlsRunnable); // Remove any existing hide callbacks
            touchpadView.setOnTouchListener(null); // Remove the touch listener
        }
    }

    private void showInputControls(ControlsProfile profile) {
        inputControlsView.setVisibility(View.VISIBLE);
        inputControlsView.requestFocus();
        inputControlsView.setProfile(profile);

        touchpadView.setSensitivity(profile.getCursorSpeed() * globalCursorSpeed);
        touchpadView.setPointerButtonRightEnabled(false);

        inputControlsView.invalidate();
    }

    private void hideInputControls() {
        inputControlsView.setShowTouchscreenControls(true);
        inputControlsView.setVisibility(View.GONE);
        inputControlsView.setProfile(null);

        touchpadView.setSensitivity(globalCursorSpeed);
        touchpadView.setPointerButtonLeftEnabled(true);
        touchpadView.setPointerButtonRightEnabled(true);

        inputControlsView.invalidate();
    }

    private void extractGraphicsDriverFiles() {
        String adrenoToolsDriverId = "";
        String selectedDriverVersion;

        String currentWrapperVersion = graphicsDriverConfig.get("version");
        selectedDriverVersion = currentWrapperVersion;

        if (shortcut != null) {
            currentWrapperVersion = shortcut.getExtra("wrapperGraphicsDriverVersion", graphicsDriverConfig.get("version"));
            selectedDriverVersion = currentWrapperVersion;
        }

        adrenoToolsDriverId = (selectedDriverVersion.contains(DefaultVersion.WRAPPER)) ? DefaultVersion.WRAPPER : selectedDriverVersion;
        Log.d("GraphicsDriverExtraction", "Adrenotools DriverID: " + adrenoToolsDriverId);

        File rootDir = imageFs.getRootDir();

        if (dxwrapper.equals("dxvk")) {
            DXVKConfigDialog.setEnvVars(this, dxwrapperConfig, envVars);
        } else if (dxwrapper.equals("vkd3d")) {
            VKD3DConfigDialog.setEnvVars(this, dxwrapperConfig, envVars);
        }

        if (!envVars.has("MESA_VK_WSI_PRESENT_MODE")) envVars.put("MESA_VK_WSI_PRESENT_MODE", "mailbox");

        boolean useDRI3 = preferences.getBoolean("use_dri3", true);
        if (!useDRI3) {
            envVars.put("MESA_VK_WSI_DEBUG", "sw");
        }

        envVars.put("VK_ICD_FILENAMES", imageFs.getShareDir() + "/vulkan/icd.d/wrapper_icd.aarch64.json");
        envVars.put("GALLIUM_DRIVER", "zink");
        envVars.put("LIBGL_KOPPER_DISABLE", "true");

        if (firstTimeBoot) {
            Log.d("XServerDisplayActivity", "First time container boot, re-extracting wrapper");
            TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "graphics_driver/wrapper" + ".tzst", rootDir);
            TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "graphics_driver/extra_libs" + ".tzst", rootDir);
        }

        if (adrenoToolsDriverId != "System") {
            AdrenotoolsManager adrenotoolsManager = new AdrenotoolsManager(this);
            adrenotoolsManager.setDriverById(envVars, imageFs, adrenoToolsDriverId);
        }
        String blacklistedExtensions = graphicsDriverConfig.get("blacklistedExtensions");
        envVars.put("WRAPPER_EXTENSION_BLACKLIST", blacklistedExtensions);

        String maxDeviceMemory = graphicsDriverConfig.get("maxDeviceMemory");
        if (maxDeviceMemory != null && Integer.parseInt(maxDeviceMemory) > 0)
            envVars.put("UTIL_LAYER_VMEM_MAX_SIZE", maxDeviceMemory);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

    private void showTouchpadHelpDialog() {
        ContentDialog dialog = new ContentDialog(this, R.layout.touchpad_help_dialog);
        dialog.setTitle(R.string.touchpad_help);
        dialog.setIcon(R.drawable.icon_help);
        dialog.findViewById(R.id.BTCancel).setVisibility(View.GONE);
        dialog.show();
    }

//    @Override
//    public boolean dispatchGenericMotionEvent(MotionEvent event) {
//        return !winHandler.onGenericMotionEvent(event) && !touchpadView.onExternalMouseEvent(event) && super.dispatchGenericMotionEvent(event);
//    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        boolean handledByWinHandler = false;
        boolean handledByTouchpadView = false;

        // Let winHandler process the event if available
        if (winHandler != null) {
            handledByWinHandler = winHandler.onGenericMotionEvent(event);
            if (handledByWinHandler) {
                //Log.d("XServerDisplayActivity", "Event handled by winHandler");
            }
        }

        // Let touchpadView process the event if available
        if (touchpadView != null) {
            handledByTouchpadView = touchpadView.onExternalMouseEvent(event);
            if (handledByTouchpadView) {
                //Log.d("XServerDisplayActivity", "Event handled by touchpadView");
            }
        }

        // Pass the event to the super method to ensure system-level handling
        boolean handledBySuper = super.dispatchGenericMotionEvent(event);
        if (!handledBySuper) {
            //Log.d("XServerDisplayActivity", "Event not handled by super");
        }

        // Combine the results: any handler consuming the event indicates it was handled
        return handledByWinHandler || handledByTouchpadView || handledBySuper;
    }


    private static final int RECAPTURE_DELAY_MS = 10000; // 10 seconds

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        // Handle the PlayStation or Xbox Home button to open the drawer
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_MODE || event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
                openXServerDrawer(); // Method to open the XServer drawer
                return true; // Indicate the event was handled
            }
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Release pointer capture when Volume Down key is pressed
            if (touchpadView != null && pointerCaptureRequested) {
                touchpadView.releasePointerCapture();
                touchpadView.setOnCapturedPointerListener(null);
                pointerCaptureRequested = false;

                // Show toast message for pointer release
                showToast(this, "Pointer capture released for 10 seconds");

                // Schedule recapture after 10 seconds
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (touchpadView != null) {
                        touchpadView.requestPointerCapture();
                        touchpadView.setOnCapturedPointerListener(new View.OnCapturedPointerListener() {
                            @Override
                            public boolean onCapturedPointer(View view, MotionEvent event) {
                                handleCapturedPointer(event);
                                return true;
                            }
                        });
                        pointerCaptureRequested = true;

                        // Show toast message for pointer recapture
                        showToast(this, "Pointer re-captured. If not working, press again to release and re-capture");
                    }
                }, RECAPTURE_DELAY_MS);

                return true; // Indicate that the event was handled
            }
        }

        // **NEW: Check if the floating view is visible and forward the key event to it**
        if (winetricksFloatingView != null && winetricksFloatingView.getVisibility() == View.VISIBLE) {
            if (winetricksFloatingView.dispatchKeyEvent(event)) {
                return true; // Indicate the floating view handled the event
            }
        }

        // Fallback to existing input handling
        return (!inputControlsView.onKeyEvent(event) && !winHandler.onKeyEvent(event) && xServer.keyboard.onKeyEvent(event)) ||
                (!ExternalController.isGameController(event.getDevice()) && super.dispatchKeyEvent(event));
    }



    public InputControlsView getInputControlsView() {
        return inputControlsView;
    }

    private void generateWineprefix() {
        Intent intent = getIntent();

        final File rootDir = imageFs.getRootDir();
        final File installedWineDir = imageFs.getInstalledWineDir();
        wineInfo = intent.getParcelableExtra("wine_info");
        envVars.put("WINEARCH", wineInfo.isWin64() ? "win64" : "win32");
        imageFs.setWinePath(wineInfo.path);

        final File containerPatternDir = new File(installedWineDir, "/preinstall/container-pattern");
        if (containerPatternDir.isDirectory()) FileUtils.delete(containerPatternDir);
        containerPatternDir.mkdirs();

        File linkFile = new File(rootDir, ImageFs.HOME_PATH);
        linkFile.delete();
        FileUtils.symlink(".."+FileUtils.toRelativePath(rootDir.getPath(), containerPatternDir.getPath()), linkFile.getPath());

        GuestProgramLauncherComponent guestProgramLauncherComponent = environment.getComponent(GuestProgramLauncherComponent.class);
//        guestProgramLauncherComponent.setGuestExecutable(wineInfo.getExecutable(this, false)+" explorer /desktop=shell,"+Container.DEFAULT_SCREEN_SIZE+" winecfg");
        guestProgramLauncherComponent.setGuestExecutable("wineboot -u explorer /desktop=shell,"+Container.DEFAULT_SCREEN_SIZE+" winecfg");

        final PreloaderDialog preloaderDialog = new PreloaderDialog(this);
        guestProgramLauncherComponent.setTerminationCallback((status) -> Executors.newSingleThreadExecutor().execute(() -> {
            if (status > 0) {
                showToast(this, R.string.unable_to_install_wine);
                FileUtils.delete(new File(installedWineDir, "/preinstall"));
                AppUtils.restartApplication(this);
                return;
            }

            preloaderDialog.showOnUiThread(R.string.finishing_installation);
            FileUtils.writeString(new File(rootDir, ImageFs.WINEPREFIX+"/.update-timestamp"), "disable\n");

            File userDir = new File(rootDir, ImageFs.WINEPREFIX+"/drive_c/users/xuser");
            File[] userFiles = userDir.listFiles();
            if (userFiles != null) {
                for (File userFile : userFiles) {
                    if (FileUtils.isSymlink(userFile)) {
                        String path = userFile.getPath();
                        userFile.delete();
                        (new File(path)).mkdirs();
                    }
                }
            }

            String suffix = wineInfo.fullVersion()+"-"+wineInfo.getArch();
            File containerPatternFile = new File(installedWineDir, "/preinstall/container-pattern-"+suffix+".tzst");
            TarCompressorUtils.compress(TarCompressorUtils.Type.ZSTD, new File(rootDir, ImageFs.WINEPREFIX), containerPatternFile, MainActivity.CONTAINER_PATTERN_COMPRESSION_LEVEL);

            if (!containerPatternFile.renameTo(new File(installedWineDir, containerPatternFile.getName())) ||
                    !(new File(wineInfo.path)).renameTo(new File(installedWineDir, wineInfo.identifier()))) {
                containerPatternFile.delete();
            }

            FileUtils.delete(new File(installedWineDir, "/preinstall"));

            preloaderDialog.closeOnUiThread();
            AppUtils.restartApplication(this, R.id.main_menu_settings);
        }));
    }

    private static final String TAG = "DXWrapperExtraction";

    private void extractDXWrapperFiles(String dxwrapper) {
        final String[] dlls = {"d3d10.dll", "d3d10_1.dll", "d3d10core.dll", "d3d11.dll", "d3d12.dll", "d3d12core.dll", "d3d8.dll", "d3d9.dll", "dxgi.dll"};

        File rootDir = imageFs.getRootDir();
        File windowsDir = new File(rootDir, ImageFs.WINEPREFIX + "/drive_c/windows");

        if (dxwrapper.contains("vkd3d")) {
            ContentProfile profile = contentsManager.getProfileByEntryName(dxwrapper);
            Log.d(TAG, "Extracting DXVK 2.4.1");
            TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "dxwrapper/dxvk-2.4.1" + ".tzst", windowsDir, onExtractFileListener);
            if (profile != null) {
                Log.d(TAG, "Applying user-defined VKD3D content profile: " + dxwrapper);
                contentsManager.applyContent(profile);
            } else {
                Log.d(TAG, "Extracting fallback VKD3D .tzst archive: " + dxwrapper);
                TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "dxwrapper/" + dxwrapper + ".tzst", windowsDir, onExtractFileListener);
            }
            Log.d(TAG, "Finished VKD3D extraction for " + dxwrapper);
        } else if (dxwrapper.contains("dxvk")) {
            Log.d(TAG, "Extracting DXVK wrapper files, version: " + dxwrapper);

            ContentProfile profile = contentsManager.getProfileByEntryName(dxwrapper);
            if (profile != null) {
                Log.d(TAG, "Applying user-defined DXVK content profile: " + dxwrapper);
                contentsManager.applyContent(profile);
            } else {
                Log.d(TAG, "Extracting fallback DXVK .tzst archive: " + dxwrapper);
                TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "dxwrapper/" + dxwrapper + ".tzst", windowsDir, onExtractFileListener);

                if (compareVersion(StringUtils.parseNumber(dxwrapper), "2.4") < 0) {
                    Log.d(TAG, "Extracting d8vk as part of DXVK version " + dxwrapper);
                    TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "dxwrapper/d8vk-" + DefaultVersion.D8VK + ".tzst", windowsDir, onExtractFileListener);
                }
            }
        } else if (dxwrapper.contains("wined3d")) {
            Log.d(TAG, "Restoring original DLL files for wined3d.");
            restoreOriginalDllFiles(dlls);
        }
    }

    private void extractDDrawrapperFiles(String ddrawrapper) {
        final String[] dlls = {"ddraw.dll","d3dimm.dll"};
        final String[] glideDlls = {"glide.dll", "glide2x.dll", "glide3x.dll", "3DfxSpl.dll", "3DfxSpl2.dll", "3DfxSpl3.dll"};

        File rootDir = imageFs.getRootDir();
        File windowsDir = new File(rootDir, ImageFs.WINEPREFIX + "/drive_c/windows");

        Log.d("XServerDisplayActivity", "Deleting glide dlls before extraction");
        for (String glideDLL : glideDlls) {
            FileUtils.delete(new File(windowsDir + "/syswow64/" + glideDLL));
        }

        if (ddrawrapper.equals("wined3d")) {
            Log.d("XserverDisplayActivity", "Restoring original dlls for WineD3D");
            restoreOriginalDllFiles(dlls);
        }
        else {
            Log.d("XServerDisplayActivity", "Extracting ddrawrapper " + ddrawrapper);
            TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "ddrawrapper/" + ddrawrapper + ".tzst", windowsDir, onExtractFileListener);
        }

        Log.d("XServerDisplayActivity", "Extracting nglide wrapper");
        TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "ddrawrapper/nglide.tzst", windowsDir, onExtractFileListener);
    }


    private static int compareVersion(String varA, String varB) {
        final String[] levelsA = varA.split("\\.");
        final String[] levelsB = varB.split("\\.");
        int minLen = Math.min(levelsA.length, levelsB.length);
        int numA, numB;

        for (int i = 0; i < minLen; i++) {
            numA = Integer.parseInt(levelsA[i]);
            numB = Integer.parseInt(levelsB[i]);
            if (numA != numB)
                return numA - numB;
        }

        if (levelsA.length != levelsB.length)
            return levelsA.length - levelsB.length;

        return 0;
    }

    private void extractWinComponentFiles() {
        Log.d("XServerDisplayActivity", "Extracting WinComponents");
        File rootDir = imageFs.getRootDir();
        File windowsDir = new File(rootDir, ImageFs.WINEPREFIX+"/drive_c/windows");
        File systemRegFile = new File(rootDir, ImageFs.WINEPREFIX+"/system.reg");

        try {
            JSONObject wincomponentsJSONObject = new JSONObject(FileUtils.readString(this, "wincomponents/wincomponents.json"));
            ArrayList<String> dlls = new ArrayList<>();
            String wincomponents = shortcut != null ? shortcut.getExtra("wincomponents", container.getWinComponents()) : container.getWinComponents();

            Iterator<String[]> oldWinComponentsIter = new KeyValueSet(container.getExtra("wincomponents", Container.FALLBACK_WINCOMPONENTS)).iterator();

            for (String[] wincomponent : new KeyValueSet(wincomponents)) {
                if (wincomponent[1].equals(oldWinComponentsIter.next()[1]) && !firstTimeBoot) continue;
                String identifier = wincomponent[0];
                boolean useNative = wincomponent[1].equals("1");

                if (!wineInfo.isArm64EC() && identifier.contains("opengl") && useNative)
                    continue;

                if (useNative) {
                    TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "wincomponents/"+identifier+".tzst", windowsDir, onExtractFileListener);
                }
                else {
                    JSONArray dlnames = wincomponentsJSONObject.getJSONArray(identifier);
                    for (int i = 0; i < dlnames.length(); i++) {
                        String dlname = dlnames.getString(i);
                        dlls.add(!dlname.endsWith(".exe") ? dlname+".dll" : dlname);
                    }
                }
                Log.d("XServerDisplayActivity", "Setting wincomponent " + identifier + " to " + String.valueOf(useNative));
                WineUtils.overrideWinComponentDlls(this, container, identifier, useNative);
                WineUtils.setWinComponentRegistryKeys(systemRegFile, identifier, useNative, this);
            }

            if (!dlls.isEmpty()) restoreOriginalDllFiles(dlls.toArray(new String[0]));
        }
        catch (JSONException e) {}
    }

    private void restoreOriginalDllFiles(final String... dlls) {
        File rootDir = imageFs.getRootDir();
        File windowsDir = new File(rootDir, ImageFs.WINEPREFIX+"/drive_c/windows");
        File system32dlls = null;
        File syswow64dlls = null;

        if (wineInfo.isArm64EC())
            system32dlls = new File(imageFs.getWinePath() + "/lib/wine/aarch64-windows");
        else
            system32dlls = new File(imageFs.getWinePath() + "/lib/wine/x86_64-windows");

        syswow64dlls = new File(imageFs.getWinePath() + "/lib/wine/i386-windows");


        for (String dll : dlls) {
            File srcFile = new File(system32dlls, dll);
            File dstFile = new File(windowsDir, "system32/" + dll);
            FileUtils.copy(srcFile, dstFile);
            srcFile = new File(syswow64dlls, dll);
            dstFile = new File(windowsDir, "syswow64/" + dll);
            FileUtils.copy(srcFile, dstFile);
        }
   }

    private boolean isGenerateWineprefix() {
        return getIntent().getBooleanExtra("generate_wineprefix", false);
    }

    private String getWineStartCommand() {
        // Initialize overrideEnvVars if not already done
        EnvVars envVars = getOverrideEnvVars();

        // Define default arguments
        String args = "";

        if (shortcut != null) {
            String execArgs = shortcut.getExtra("execArgs");
            execArgs = !execArgs.isEmpty() ? " " + execArgs : "";

            if (shortcut.path.endsWith(".lnk")) {
                args += "\"" + shortcut.path + "\"" + execArgs;
            } else {
                String exeDir = FileUtils.getDirname(shortcut.path);
                String filename = FileUtils.getName(shortcut.path);

                int dotIndex = filename.lastIndexOf(".");
                int spaceIndex = (dotIndex != -1) ? filename.indexOf(" ", dotIndex) : -1;

                if (spaceIndex != -1) {
                    execArgs = filename.substring(spaceIndex + 1) + execArgs;
                    filename = filename.substring(0, spaceIndex);
                }

                args += "/dir " + StringUtils.escapeDOSPath(exeDir) + " \"" + filename + "\"" + execArgs;
            }
        } else {
            // Append EXTRA_EXEC_ARGS from overrideEnvVars if it exists
            if (envVars.has("EXTRA_EXEC_ARGS")) {
                args += " " + envVars.get("EXTRA_EXEC_ARGS");
                envVars.remove("EXTRA_EXEC_ARGS"); // Remove the key after use
            } else {
                args += "\"wfm.exe\"";
            }
        }
        // Construct the final command
        String command = "winhandler.exe " + args;
        Log.d("Winetricks", "Wine Start Command: " + command);

        return command;
    }

    private String getExecutable() {
        String filename = "";
        if (shortcut != null) {
            filename = FileUtils.getName(shortcut.path);
        }
        else if (isGenerateWineprefix()) {
            filename = "wineboot.exe";
        }
        else
            filename = "wfm.exe";
        return filename;
    }


    public XServer getXServer() {
        return xServer;
    }

    public WinHandler getWinHandler() {
        return winHandler;
    }

    public XServerView getXServerView() {
        return xServerView;
    }

    public Container getContainer() {
        return container;
    }

    public void setDXWrapper(String dxwrapper) {
        this.dxwrapper = dxwrapper;
    }

    public EnvVars getOverrideEnvVars() {
        if (overrideEnvVars == null) {
            overrideEnvVars = new EnvVars();
        }
        return overrideEnvVars;
    }

    private void changeWineAudioDriver() {
        if (!audioDriver.equals(container.getExtra("audioDriver"))) {
            File rootDir = imageFs.getRootDir();
            File userRegFile = new File(rootDir, ImageFs.WINEPREFIX+"/user.reg");
            try (WineRegistryEditor registryEditor = new WineRegistryEditor(userRegFile)) {
                if (audioDriver.equals("alsa")) {
                    registryEditor.setStringValue("Software\\Wine\\Drivers", "Audio", "alsa");
                }
                else if (audioDriver.equals("pulseaudio")) {
                    registryEditor.setStringValue("Software\\Wine\\Drivers", "Audio", "pulse");
                }
            }
            container.putExtra("audioDriver", audioDriver);
            container.saveData();
        }
    }

    private void applyGeneralPatches(Container container) {
        File rootDir = imageFs.getRootDir();
        TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "container_pattern_common.tzst", rootDir);
        TarCompressorUtils.extract(TarCompressorUtils.Type.ZSTD, this, "pulseaudio.tzst", new File(getFilesDir(), "pulseaudio"));
        WineUtils.applySystemTweaks(this, wineInfo);
        container.putExtra("graphicsDriver", null);
        container.putExtra("desktopTheme", null);
    }

//    private void assignTaskAffinity(Window window) {
//        if (taskAffinityMask == 0) return;
//        int processId = window.getProcessId();
//        String className = window.getClassName();
//        int processAffinity = window.isWoW64() ? taskAffinityMaskWoW64 : taskAffinityMask;
//
//        if (processId > 0) {
//            winHandler.setProcessAffinity(processId, processAffinity);
//        }
//        else if (!className.isEmpty()) {
//            winHandler.setProcessAffinity(window.getClassName(), processAffinity);
//        }
//    }

    private void changeFrameRatingVisibility(Window window, Property property) {
        if (frameRating == null) return;

        if (property != null) {
            if (frameRatingWindowId == -1 && (property.nameAsString().contains("_UTIL_LAYER") || property.nameAsString().contains("_MESA_DRV"))) {
                frameRatingWindowId = window.id;
                Log.d("XServerDisplayActivity", "Showing hud for Window " + window.getName());
                frameRating.update();
            }
            if (property.nameAsString().contains("_UTIL_LAYER_ENGINE_NAME")) {
                frameRating.setRenderer(property.toString());
            }
            if (property.nameAsString().contains("_UTIL_LAYER_GPU_NAME")) {
                frameRating.setGpuName(property.toString());
            }
        }
        else if (frameRatingWindowId != -1) {
            frameRatingWindowId = -1;
            Log.d("XServerDisplayActivity", "Hiding hud for Window " + window.getName());
            runOnUiThread(() -> frameRating.setVisibility(View.GONE));
            frameRating.reset();
        }
    }

    private void scheduleSecondaryExecution(String secondaryExec, int delaySeconds) {
        if (winHandler != null) {
            winHandler.execWithDelay(secondaryExec, delaySeconds);
            Log.d("XServerDisplayActivity", "Scheduled secondary execution: " + secondaryExec + " with delay: " + delaySeconds);
        } else {
            Log.e("XServerDisplayActivity", "WinHandler is null, cannot schedule secondary execution.");
        }
    }

    public String getScreenEffectProfile() {
        return screenEffectProfile;
    }

    public void setScreenEffectProfile(String screenEffectProfile) {
        this.screenEffectProfile = screenEffectProfile;
    }

    // maybe we can remove this or maybe i will create it...
//    public void clearContainerCache(Container container){//        File rootDir = container.getRootDir();
//        final File cacheDir = new File(rootDir, ".cache");
//        FileUtils.clear(cacheDir);
//    }

}
