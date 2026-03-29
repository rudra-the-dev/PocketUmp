# 📱 PocketUmp

### Turn Any Street Into a Smart Cricket Ground

PocketUmp is a mobile-first cricket officiating and analytics system designed for informal matches like gully cricket. It uses computer vision, motion tracking, and cloud-based physics to bring fair decisions and structured gameplay to unstructured environments.

---

## 🚀 Overview

PocketUmp transforms a smartphone into a virtual cricket umpire. By combining on-device processing with cloud-based computation, it can:

- Track ball movement  
- Estimate shot trajectory  
- Detect outs and rule violations  
- Map irregular playing environments  
- Reduce disputes during matches  

---

## ⚙️ Core Features

### 🧭 Guided 3D Street Mapping
- Interactive camera-based scanning  
- Real-time instructions (turn left/right, detect blind spots)  
- Builds a simplified 3D model of the playing area  

---

### 🎯 Virtual Boundary System
- Uses projectile motion to estimate shot distance  
- Determines boundaries (4/6) based on virtual trajectory  
- Works in small or obstructed environments  

---

### 🧠 Hybrid AI Architecture
- **On-device (Edge)**:
  - Gesture detection (e.g., DRS trigger)  
  - Basic object tracking  
- **Cloud backend**:
  - Trajectory prediction  
  - Physics simulation  
  - Advanced decision logic  

---

### ⚖️ Automated Umpiring
- No-ball detection (foot placement vs release timing)  
- Boundary decisions  
- Run tracking and basic event detection  

---

### 🔊 Audio-Visual Detection
- Detects bat-ball contact using sound + motion  
- Improves accuracy of edge/snicks detection  

---

## 🏗️ Architecture

Mobile App (Kotlin)
- Camera Input  
- ARCore Tracking  
- Gesture Recognition  
- Local Processing (TFLite)  

        ↓

Cloud Backend (FastAPI)
- Trajectory Engine  
- Physics Simulation  
- Event Detection  
- Decision Logic  

        ↓

Output
- Umpire Decisions  
- Visual Feedback  
- Match Data  

---

## 🎮 Use Cases

- Gully cricket matches  
- Local tournaments  
- Solo practice sessions  
- Match recording and analysis  

---

## 🧱 Design Principles

- Hardware Agnostic → Works on low-end devices (cloud support)  
- Environment Adaptive → Calibrates to any street  
- Real-Time Feedback → Fast decisions  
- User-Guided → Assisted scanning for accuracy  

---

## ⚠️ Limitations

- Accuracy depends on lighting and camera quality  
- Audio detection may be affected by noise  
- Internet required for advanced features  
- Complex environments may reduce precision  

---

## 🔮 Future Scope

- Multiplayer sync & scoreboards  
- Auto highlight generation  
- Voice commands  
- Tournament mode  
- AI-based coaching insights
