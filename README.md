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
- **Android Studio** (Java/Kotlin)
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

## Screenshots

![IMG-20241216-WA0012](https://github.com/user-attachments/assets/633bc169-12cf-42ce-84db-57c1a36858e5)
![IMG-20241216-WA0013](https://github.com/user-attachments/assets/f43698f6-5445-4470-9ad8-95160567d1dc)
![IMG-20241216-WA0014](https://github.com/user-attachments/assets/a1c6812b-0f9d-41dd-867a-a78860d6a5cf)
![IMG-20241216-WA0015](https://github.com/user-attachments/assets/11ce8c8c-6882-4f3e-9c80-0ffae251c641)
![IMG-20241216-WA0016](https://github.com/user-attachments/assets/7a9c4efc-a4f9-4ae8-944a-d3ab59e9e4be)
![IMG-20241216-WA0017](https://github.com/user-attachments/assets/22b13f71-d30a-4af0-a1e7-65d69dd5a9c1)
![IMG-20241216-WA0018](https://github.com/user-attachments/assets/7d81c498-5c49-4751-a545-5a5387c942c1)
![IMG-20241216-WA0019](https://github.com/user-attachments/assets/524dad08-116b-4651-b22b-ae3b29c6e9c4)
![IMG-20241216-WA0020](https://github.com/user-attachments/assets/558e37b4-9359-415c-aae4-fa6fe41406e0)
![IMG-20241216-WA0021](https://github.com/user-attachments/assets/102c562c-b1d6-4c79-8d66-f69eae41bd2a)
![IMG-20241216-WA0022](https://github.com/user-attachments/assets/3ddf877e-beef-45f3-9266-8dc4a88470b0)
![IMG-20241216-WA0023](https://github.com/user-attachments/assets/b8b83728-23ae-46dd-b47f-6912da74b7f2)
![IMG-20241216-WA0024](https://github.com/user-attachments/assets/1953c187-a490-45df-8906-dff5e6d5bb2e)
![IMG-20241216-WA0025](https://github.com/user-attachments/assets/f42e9de3-4832-4a9b-9f5a-730e89adc976)
![IMG-20241216-WA0026](https://github.com/user-attachments/assets/5925918c-1c3c-4011-a597-c9531d1e062e)
![IMG-20241216-WA0027](https://github.com/user-attachments/assets/75c64ffd-1cbe-4eda-99b5-36990ac21f82)
![IMG-20241216-WA0028](https://github.com/user-attachments/assets/cc51d71f-322f-4282-9acf-836efaffd04d)

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
- **Email**: your-email@example.com
- **GitHub**: [yourusername](https://github.com/yourusername)

---

Happy Shopping with Dressify! 🎉
