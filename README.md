
# SKELETON PROJECT
Base architecture for 2019 Android app

## How to use
Clone this repo, then run the following commands:
```
rm -rf .git
git init
```
You will need to refactor the code to use the correct package name for your project. 
  
## Dagger  
This project has already dagger setup. It uses dagger-android.   
When adding a **new fragment** you should extend BaseFragment and declare it in the FragmentBuildersModule class.  
When adding a **new viewModel** you need to also declare it in the ViewModelModule class.

## Navigation
This project uses the Navigation component.

You will find the main graph in the **nav_graph file**. It contains the splash which can navigate to either the login nested flow or the MainFragment.
The MainFragment has a BottomNavigationView and is setup with the **tab_nav_graph**.

The navigation inside a tab should be isolated in its own nested graph for clarity.

Pressing the back button will always be consumed by the top NavController. If you wish to be able to back inside a subflow, you can register the custom callback available in BaseFragment.

```
override fun onActivityCreated(savedInstanceState: Bundle?) {  
    super.onActivityCreated(savedInstanceState)  
    requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallBackNavControllerOrParent())  
}
```

## LifeCycle
The **LoginViewModel** uses the activity LifeCycle and is available everywhere in the app. It controls wether the user is logged in or not.
The **ProfileViewModel** can be used to store data about the current user. Its LifeCycle should be the same as the **tab_nav_graph**.
```
private val profileViewModel: ProfileViewModel by navGraphViewModels(R.id.tab_nav_graph) { viewModelFactory }
```
Thus it will stay alive will the user is navigating in the app, but will be destroyed upon logout.
If you scope a ViewModel to a nested flow, it will be cleared when the user exist the flow.


## Glide
Glide is ready to be used, so you should use it if you need to download an image:
```
Glide.with(itemView)  
    .load(generateVideoThumbnail(contestCaseMedia.documentFilePath))  
    .centerCrop()  
    .into(binding.ivPreview)
```

## Room
Room is also setup, you just need to add your entities in the AppDatabase class (don't forget to remove the example class).

## Timber
Timber is used to do the logging of the app. **Never use Log.x**, you can simply Use **Timber.x**. No need to define a TAG anymore, Timber will do this for us.

## Dexter
The Dexter library is available if you need to ask some runtime permission:
```
Dexter.withActivity(requireActivity())  
    .withPermissions(  
        Manifest.permission.CAMERA,  
  Manifest.permission.RECORD_AUDIO,  
  Manifest.permission.ACCESS_FINE_LOCATION  
  ).withListener(object: MultiplePermissionsListener {  
        @SuppressLint("MissingPermission")  
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {  
            if (report.areAllPermissionsGranted()) {  
                Timber.d("Permission was granted")  
            } else {  
                Toast.makeText(requireContext(), R.string.permission_location_not_granted, Toast.LENGTH_LONG).show()  
            }  
        }  
  
        override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken) {  
            AlertDialog.Builder(requireContext())  
                .setTitle(R.string.permission_dialog_title)  
                .setMessage(R.string.permission_start_recording_message)  
                .setPositiveButton(R.string.ok) { _, _ ->  
  token.continuePermissionRequest()  
                }.setNegativeButton(R.string.cancel) { _, _ ->  
  token.cancelPermissionRequest()  
                }.show()  
        }  
    }).check()
```

## ThreeTen
ThreeTen is a backport of the JSR310 java.time to any android version. When dealing with date **you should always avoid Date, Calendar** and instead use **Instant, DateTime, LocalDateTime, etc.**
Of course, be sure to pick the class from the package **org.threeten.bp**. They are the same as the one found under java.time but do not require API 26.

## LeakCanary
When developing, if you are unsure about memory leak, you can add this dependency to the project:
`debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.0-alpha-3'`

## PlayStore Publish
The project is already setup to ease app publication. You just need to generate a jks and put it under **app/keystore/**. Then fill the correct values in **singingConfigs** of the app/build.gradle. Then thanks to the **Build Variants** you will be able to generate a signed APK with production URL.