# DigiApp
Android Application for Apply Digital test

Develop Notes:
- Developed with Android Studio Dolphin #AI-213.7172.25.2113.9014738 (August 31, 2022)
- minSdk 21
- targetSdk 33
- Gradle 7.4
- Android Plugin 7.3.0

App Notes (Extras):
- For the date showed in the subtitle: 
  - When it's a later date than the current one, the article shows "now"
  - When it has more than one day but less than 2 days, the article shows "yesterday"
  - For other cases, it shows seconds, minutes, hours or days past
- For the delete article functionality it only takes a swipe to the left (no button or warning popup)
- Offline mode shows a small yellow label at the bottom of both screens
- Webview saves cache when opened with internet
