# 🖱️ Mouse-Camera-ASL-Keyboard

Control your computer with your hand through a webcam. Move the cursor, click with gestures, and train your own ASL signs to use as a keyboard.

The hand tracking runs in **Python** (OpenCV + MediaPipe). A **JavaFX** control panel lets you launch each script, change settings, and export your data.

## ✨ Features

- 🖐️ Real-time hand tracking through a webcam
- 🖱️ Move the mouse with your index finger
- 👆 Gesture-based left and right clicking
- 🔤 Train your own ASL signs and test them live
- ⚙️ Control panel with Camera and Landmarks toggles
- 💾 Download your trained signs (`signs.jsonl`) and settings (`config.json`)

## 🛠️ Technologies

| Part | Tools |
|------|-------|
| Hand tracking | Python 3.12, OpenCV, MediaPipe, PyAutoGUI |
| Control panel | Java, JavaFX, Maven |
| Settings | Gson (`config.json`) |

## 📋 Requirements

- **Python 3.12** (MediaPipe and OpenCV may not support the newest Python releases yet)
- **JDK 21 or newer** and Maven (the included `mvnw` wrapper works)
- A webcam

## 🚀 Getting Started

**1. Clone the repository**

```
git clone https://github.com/VampireByte-Studio/Mouse-Camera-ASL-Keyboard.git
cd Mouse-Camera-ASL-Keyboard
```

**2. Create a Python environment and install the packages**

```
py -3.12 -m venv venv
venv\Scripts\activate
pip install opencv-python mediapipe pyautogui numpy
```

Keep the environment named `venv` in the project root. The app looks for `venv\Scripts\python.exe`.

**3. Add the MediaPipe model**

Download `hand_landmarker.task` from the [MediaPipe Hand Landmarker page](https://ai.google.dev/edge/mediapipe/solutions/vision/hand_landmarker) and place it next to the Python scripts:

```
src/main/java/org/mouse/mousecameraapp/functions/scripts/hand_landmarker.task
```

**4. Run the app**

Open the project in IntelliJ IDEA, let Maven finish loading, and run `MainApp`.

## 🕹️ Using the App

| Button | What it does | Script |
|--------|--------------|--------|
| **Launch** | Starts the hand-controlled mouse | `Hand_Mouse.py` |
| **Train** | Record your own signs | `Hand_Recorder.py` |
| **Test** | Check how well your signs are recognised | `Hand_Tester.py` |
| **Camera / Landmarks** | Toggle the camera preview and landmark dots | writes `config.json` |
| **Download Signs File** | Save a copy of `signs.jsonl` | |
| **Download Config File** | Save a copy of `config.json` | |

Use **Train** before **Test**: the tester compares your hand against the samples in `signs.jsonl`, so it needs recorded signs first. Only one script can use the webcam at a time, so close one before starting another.

You can also run the scripts without the app:

```
venv\Scripts\python.exe src\main\java\org\mouse\mousecameraapp\functions\scripts\Hand_Mouse.py
```

## 🎮 Mouse Controls

To control the mouse you have to program using the Train module
1 - controls left click
2 - controls right click
3- Switch between ASL and Mouse


## 🧠 Train Module

To use properly launch either via java app or manually run the script, Press the letter/number you want to record.

then press "Spacebar" to start recording hand positions to train, to test it you can run the test script and input the expected results 
and see if the tester is picking up the correct inputs.



## 🧠 How It Works

```
Webcam → OpenCV → MediaPipe hand landmarks → gesture / sign recognition → mouse movement, click, or letter
```

- MediaPipe finds 21 landmarks on your hand in each frame.
- For the mouse, the index fingertip position is mapped to screen coordinates.
- For ASL signs, the landmarks are normalised and compared with your recorded samples in `signs.jsonl`. The closest matches vote on the sign.

## ⚙️ Configuration

The control panel writes `config.json` in the project root:

```json
{
  "camera": true,
  "landmarks": false
}
```

`Hand_Mouse.py` finds this file on its own (it looks for the folder containing `pom.xml`) and reads it when it starts.

## 🗂️ Project Layout

```
Mouse-Camera-ASL-Keyboard/
├── pom.xml
├── signs.jsonl                  your recorded signs
├── config.json                  created by the app
├── venv/                        Python 3.12 environment (not committed)
└── src/main/java/org/mouse/mousecameraapp/
    ├── MainApp.java             JavaFX entry point
    ├── MainController.java      button handlers
    └── functions/
        ├── ConfigWriter.java
        └── scripts/
            ├── Hand_Mouse.py
            ├── Hand_Recorder.py
            ├── Hand_Tester.py
            └── hand_landmarker.task
```

## 🔧 Troubleshooting

| Problem | Fix |
|---------|-----|
| `ModuleNotFoundError` or a numpy import error | The wrong Python is being used. Make sure the environment is Python 3.12 and is named `venv` |
| `FileNotFoundError: signs.jsonl` | Record some signs with **Train** first, or copy an existing `signs.jsonl` into the project root |
| `Unable to open file ... hand_landmarker.task` | Put the model file next to the scripts (step 3) |
| Camera window never appears | Another program or script is using the webcam. Close it and try again |
| Nothing happens when a button is pressed | Check the Run window in IntelliJ for `PY:` lines, which show the Python output |

## ⚠️ Notes

Performance depends on camera quality, lighting, CPU/GPU speed, distance from the camera, and background complexity. Good lighting and a clear view of your hand give the best tracking.

## 🔒 Privacy

The webcam feed is processed locally. Nothing is uploaded to an external server.


## 📄 License

This project is for personal, educational, and non-profit use only.

You may use, copy, modify, and distribute this code for projects that are not intended to generate monetary profit. Commercial use is prohibited without prior written permission from the copyright holder. To use it commercially, contact VampireByte first.
