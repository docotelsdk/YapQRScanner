# YapQRScanner
Barcode scanning library for Android, using ZXing for decoding.

The project is loosely based on the ZXing Android Barcode Scanner application, but is not affiliated with the official ZXing project.

Features:

Can be used via Intents (little code required).
Can be embedded in an Activity, for advanced customization of UI and logic.
Scanning can be performed in landscape or portrait mode.
Camera is managed in a background thread, for fast startup time.
A sample application is available in Releases.

By default, Android SDK 19+ is required because of zxing:core 3.3.2. To support SDK 14+, downgrade zxing:core to 3.3.0 - see instructions.

## Adding aar dependency with Gradle
Only Android SDK 19+ is supported by default.

Add the following to your build.gradle file:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.docotelsdk:YapQRScanner:1.0.0'
    compile 'com.android.support:appcompat-v7:25.3.1'   // Minimum 23+ is required
}

android {
    buildToolsVersion '27.0.3' // Older versions may give compile errors
}
```

For Android 14+ support, downgrade zxing:core to 3.3.0 or earlier:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile('com.github.docotelsdk:YapQRScanner:1.0.0') { transitive = false }
    compile 'com.android.support:appcompat-v7:25.3.1'   // Version 23+ is required
    compile 'com.google.zxing:core:3.3.0'
}

android {
    buildToolsVersion '27.0.3' // Older versions may give compile errors
}
```

## Hardware Acceleration
Hardware accelation is required since TextureView is used.

Make sure it is enabled in your manifest file:

```groovy
    <application android:hardwareAccelerated="true" ... >
```

## Usage with IntentIntegrator
Launch the intent with the default options:

new IntentIntegrator(this).initiateScan(); // `this` is the current Activity

```groovy
// Get the results:
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if(result != null) {
        if(result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
```

Use from a Fragment:

```groovy
IntentIntegrator.forFragment(this).initiateScan(); // `this` is the current Fragment

// If you're using the support library, use IntentIntegrator.forSupportFragment(this) instead.
Customize options:

IntentIntegrator integrator = new IntentIntegrator(this);
integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
integrator.setPrompt("Scan a barcode");
integrator.setCameraId(0);  // Use a specific camera of the device
integrator.setBeepEnabled(false);
integrator.setBarcodeImageEnabled(true);
integrator.initiateScan();
```
