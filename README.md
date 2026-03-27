# Android E-Commerce Task (Beauty & Pharmacy)

This is an Android application built as a technical assignment. It demonstrates a **Master-Detail** flow using the [DummyJSON API](https://dummyjson.com/products), featuring product listing with pagination, detailed views, and a local favorites system.

## 🛠 Setup & Run
1. Clone the repository.
2. Open in **Android Studio (Ladybug or newer)**.
3. Sync Gradle (requires Internet for dependencies).
4. Select your preferred **Build Variant**.
5. Run on an Emulator or Physical Device (API 24+).

---
## Architecture & Tech Stack
The app follows the **MVVM** pattern with a clean separation of concerns:

- **Language:** Kotlin
- **UI:** XML Layouts with **View Binding**.
- **Asynchronous Work:** Coroutines & Flow.
- **Dependency Injection:** **Hilt** (Dagger).
- **Networking:** **Retrofit** & OkHttp.
- **Local Database:** **Room** with KSP.
- **Image Loading:** Glide.
- **Navigation:** Jetpack Navigation Component with **Safe Args**.

### Project Structure:
- `data/`: Local (Room), Remote (Retrofit), and Repositories.
- `di/`: Hilt Modules for providing singleton instances.
- `ui/`: Master (List), Detail, and Favorites fragments with their respective ViewModels.
- `model/`: Data classes for API responses and Database entities.
- `utils/`: Extension functions for mapping between layers.

---

## Key Decisions
- Single Activity Architecture: Used for smoother transitions and easier fragment management.
- **KSP (Kotlin Symbol Processing):** Used instead of KAPT for faster build times with Room and Hilt.
- **Manual Mapping:** Used extension functions (Mappers) to convert API models to Database entities
- **Theme Attributes:** Used `?attr/colorPrimary` in XML layouts to allow automatic color swapping based on the active flavor's theme.
- **Safe Args:** Used for type-safe data passing between the List and Details fragments.


## Features
- **Product List:** Fetches data from REST API with **Pagination** (limit/skip).
- **Product Details:** Detailed view with high-res images, descriptions, and pricing.
- **Favorites (Local Storage):** Users can save products locally using **Room Database**.
- **White-label Support:** Implementation of **Product Flavors** to customize branding for different partners.
- **State Handling:** Integrated Loading and Error states for a seamless user experience.

---

## White-label & Product Flavors (Bonus)
The application uses **Gradle Product Flavors** to simulate customization for different partners:
**How to switch:**
Open the **Build Variants** tab in Android Studio and select `partnerADebug` or `partnerBDebug`. Each flavor has its own `resValue` for the app name, custom `colors.xml` and icons.

---
## Staged Rollout Strategy (Bonus)
To ensure a stable release for multiple partners, the following rollout strategy is proposed:

1. **Internal Testing:** Deploy to the internal QA team via Google Play Internal Sharing.
2. **Beta Testing (10%):** Release to a subset of external testers for each partner flavor.
3. **Staged Production Rollout:**
    -  Monitor Firebase Crashlytics for any flavor-specific regressions.
    -  Gradual increase if network latency for specific BaseURLs is stable.
    - Full monitoring of API consumption limits.
    - Full release.
4. **Emergency Stop:** If a critical bug is found in one partner's API configuration, the rollout for that specific flavor can be halted without affecting others.