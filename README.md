# ChatterRPGbox

**ChatterRPGbox** is a reusable Jetpack Compose dialogue system inspired by classic 16-bit RPGs.  
It provides an old-school text crawl, animated character portraits, and user-controlled text speed , all packed in a clean, modern Compose architecture.

---

## Features

-  **Retro Dialogue Box** – text appears letter by letter, like in classic RPGs.  
-  **Animated Portraits** – simple frame-based animation (idle → talking loop).  
-  **Touch-to-Speed-Up** – accelerate the text crawl by holding down the dialogue box.  
-  **Composable Reuse** – one component for all characters.  
-  **MVVM-friendly** – easy to plug into your ViewModels and story logic.

---

## Architecture Overview

| Layer | Responsibility |
|-------|----------------|
| **UI (Compose)** | Displays the dialogue box, handles animations & input. |
| **State Management** | Uses `mutableStateOf`, `LaunchedEffect`, and coroutines for reactive updates. |
| **Assets** | Character bitmaps for frame-based mouth animation. |
| **ViewModel** | Supplies text, character info, and dialogue flow control. |

---

## Roadmap

### **1. Data & Assets**
- [ ] Prepare bitmaps: `idle`, `mouth_closed`, `mouth_open`, etc.  
- [ ] Store frames in `List<ImageBitmap>` (idle → closed → open → closed → idle).

### **2. Composable**
- [ ] Implement `DialogueBox(...)` with props:  
  `characterName`, `frames`, `text`, `textSpeed`, `frameDuration`, `onFinished`.

### **3. Internal State**
- [ ] `displayedText` → progressively revealed text.  
- [ ] `currentFrameIndex` → animated portrait frame.  
- [ ] `isTalking`, `isPressed` → handle animation & user input.

### **4. Text Crawl**
- [ ] Coroutine loop adding one letter per `textSpeed`.  
- [ ] Accelerate when `isPressed = true`.  
- [ ] Stop animation + trigger callback when done.

### **5. Frame Loop Animation**
- [ ] Coroutine loop switching frames every `frameDuration`.  
- [ ] Stop when text ends, reset to idle frame.

### **6. Interaction**
- [ ] Use `Modifier.pointerInput` + `tryAwaitRelease()` for press detection.  
- [ ] Connect to dialogue flow logic (next line, choices, etc.).

### **7. Layout**
- [ ] `Card` for text bubble.  
- [ ] Style text and name for retro readability.

### **8. Polish**
- [ ] Smooth enter/exit animations.  

---

## Tech Stack
- **Kotlin**
- **Jetpack Compose**
- **Coroutines / LaunchedEffect**
- **MVVM Architecture**

---

## 🚀 Future Plans
- Scripted dialogues via JSON or DSL.
- Themed dialogue skins (fantasy, sci-fi, horror).

---

> *"Because a single blinking pixel can tell more truth than a thousand cutscenes."*
