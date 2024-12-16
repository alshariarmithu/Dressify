# Dressify - Android E-Commerce App

Welcome to **Dressify**, your ultimate solution for a seamless online shopping experience! Dressify is an Android-based e-commerce app designed for clothing and fashion enthusiasts. With an elegant UI and dynamic features, it offers users a joyful shopping experience.

---

## Features

- **Dynamic Weather-Based Recommendations**
  - Uses a weather API to suggest clothing items suitable for the current weather conditions.

- **Welcome Activity**
  - A friendly onboarding screen with the slogan:  
    `Welcome to Dressify`
    `Happy Shopping.`

- **Product Categories**
  - Diverse clothing and accessory options tailored for all styles.

- **Quotes Integration**
  - Inspirational quotes from categories like beauty, business, and design, using the [Quotes API](https://api.api-ninjas.com/v1/quotes).

- **Firebase Integration**
  - Stores and retrieves product and user data securely in Firebase Realtime Database.
  - Images are uploaded and managed through Postimage with links stored in Firebase.

- **Responsive Design**
  - Mobile-friendly layouts with features like a bottom menu bar and search functionality.

---

## Tech Stack

### **Frontend**
- **Android Studio** (Java/)
- **XML Layouts** for UI Design

### **Backend**
- **Firebase Realtime Database** for dynamic data storage
- **Postimage** for image hosting

### **APIs**
- **Quotes API** for motivational quotes
- **Weather API** for weather-based clothing recommendations

---

## How to Run Locally

### Prerequisites
1. **Android Studio** installed on your machine.
2. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/Dressify.git
   ```
3. Import the project into Android Studio.
4. Configure your Firebase project:
   - Add the `google-services.json` file to the `app/` directory.

5. Set up your API keys:
   - **Quotes API Key**: Add your API key to the relevant `Constants` file.
   - **Weather API Key**: Obtain an API key and configure it in your project.

### Build and Run
1. Sync the Gradle files.
2. Build the project and resolve dependencies.
3. Run the app on an emulator or physical device.

---

## API Usage

### Quotes API
- Base URL: `https://api.api-ninjas.com/v1/quotes`
- Categories Used: `beauty`, `business`, `design`

Example endpoint:
```bash
https://api.api-ninjas.com/v1/quotes?category=beauty
```

### Weather API
- Retrieves weather conditions to suggest appropriate clothing.

---
## Contributing

Contributions are welcome! If you'd like to improve the app or fix bugs:
1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature"
   ```
4. Push the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

For any queries or feedback, feel free to reach out:
- **GitHub**: [alshariarmithu](https://github.com/alshariarmithu)

---

Happy Shopping with Dressify! 🎉
