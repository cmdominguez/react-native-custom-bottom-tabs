package com.custombottomtabslibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.utils.UiUtils;
import com.reactnativenavigation.views.bottomtabs.BottomTabsContainer;
import com.reactnativenavigation.views.bottomtabs.BottomTabsLayout;

@ReactModule(name = CustomBottomTabsLibraryModule.NAME)
public class CustomBottomTabsLibraryModule extends ReactContextBaseJavaModule {

  ReactApplicationContext reactApplicationContext;
  static ReactRootView mReactRootView;
  private final ReactInstanceManager mReactInstance;
  static int mTabBarSize;
  private FrameLayout mRootView;
  public static final String NAME = "CustomBottomTabsLibrary";

  public CustomBottomTabsLibraryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactApplicationContext = reactContext;
    mReactInstance = NavigationApplication
            .instance
            .getReactNativeHost()
            .getReactInstanceManager();
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private BottomTabsContainer getBottomTabsContainer(FrameLayout frameLayout){
    if(
            frameLayout.getChildCount() > 0
                    && frameLayout.getChildAt(0).getClass().getSimpleName().equals("CoordinatorLayout")
    ){
      CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.getChildAt(0);
      if(
              coordinatorLayout != null
                      && coordinatorLayout.getChildCount() > 0
                      && coordinatorLayout.getChildAt(0).getClass().getSimpleName().equals("BottomTabsLayout")
      ){
        BottomTabsLayout bottomTabsLayout = (BottomTabsLayout) coordinatorLayout.getChildAt(0);
        if(bottomTabsLayout != null && bottomTabsLayout.getChildCount() > 0){
          for (int i = 0; i < bottomTabsLayout.getChildCount(); i++) {
            View child = bottomTabsLayout.getChildAt(i);
            String classType = child.getClass().getSimpleName();
            if(classType.equals("BottomTabsContainer")){
              return (BottomTabsContainer) child;
            }
          }
        }
      }
    }
    return null;
  }


  private ReactRootView createReactNativeContainer(int customTabBarSize, String moduleName) {
    ReactRootView reactRootView = new ReactRootView(getCurrentActivity());
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            customTabBarSize > 0 ? (int) customTabBarSize : mTabBarSize
    );
    layoutParams.gravity = Gravity.BOTTOM;
    reactRootView.setLayoutParams(layoutParams);
    Bundle bundle = new Bundle();
    bundle.putString("componentId", moduleName);
    reactRootView.startReactApplication(mReactInstance, moduleName, bundle);
    return reactRootView;
  }

  @ReactMethod
  public void setBottomTabsComponent(String moduleName, int customTabBarSize) {
    Activity activity = getCurrentActivity();
    mTabBarSize = UiUtils.getBottomTabsHeight(reactApplicationContext);

    if (activity != null) {
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {

            // Activity principal
            mRootView = activity
                    .findViewById(android.R.id.content);

            // Obtengo el container de las tabs
            BottomTabsContainer btc = getBottomTabsContainer(mRootView);

            if(mReactRootView != null){
              mReactRootView.unmountReactApplication();
            }

            mReactRootView = createReactNativeContainer(customTabBarSize, moduleName);

            if (btc != null) {
                btc.removeAllViews();
                btc.addView(mReactRootView);
            }
          }
        });
    }
  }
  @ReactMethod
  public void setVisible(boolean visible){
    Activity activity = getCurrentActivity();
    if(activity == null){
      return;
    }
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mReactRootView.setVisibility(visible ? View.VISIBLE : View.GONE );
      }
    });
  }

}
