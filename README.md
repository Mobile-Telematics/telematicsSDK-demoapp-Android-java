# Mobile Telematics demo app JAVA

This is an app that demostrate using of the [Telematics SDK](https://www.telematicssdk.com/telematics-sdk/) and walk you throught the integration. The SDK tracks user location and driving behavior such as speeding, cornering, braking, distracted driving and other parameters.

## Installation
* Create a company account in [Datahub](https://userdatahub.com/)
* Obtain [credentials](https://docs.telematicssdk.com/docs/datahub#user-service-credentials)
* [Create a test user](https://docs.telematicssdk.com/docs/register-a-new-sdk-user) to obtain ```DeviceToken```
***
* Clone this repository to local folder
* Open project with Android Studio
* Open MainActivity.kt file and setup ```YOUR_TOKEN``` with your real *Devicetoken*
    ```
    private static final String YOUR_TOKEN = "";
    ```   
* build project and run
* Click on `START PERMISSIONS WIZARD` or `START PERMISSIONS DIALOG` and grant all required permissions
* Click on `ENABLE SDK` and you are ready to go!

## Use the app
* Make a trip
* Check your trip in the [Datahub](https://userdatahub.com/)


## More details on Mobile telematics
* [Telematics product page](https://telematicssdk.com)
* [Documentations](https://docs.telematicssdk.comm)
* [Telematics API reference](https://docs.telematicssdk.com/reference)
* [Telematics postman collection](https://postman.telematicssdk.com)
* [Open-source telematics app](https://www.telematicssdk.com/telematics-app/)
* [Pricing](https://www.telematicssdk.com/pricing/)
* [Contacts](https://www.telematicssdk.com/contact/)
