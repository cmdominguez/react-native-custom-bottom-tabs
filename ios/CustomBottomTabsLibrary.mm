#import "CustomBottomTabsLibrary.h"
#import <React/RCTRootView.h>
#import <ReactNativeNavigation/ReactNativeNavigation.h>
#import "Constants.h"


@implementation CustomBottomTabsLibrary
RCT_EXPORT_MODULE()

// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_EXPORT_METHOD(setBottomTabsComponent:(NSString *)moduleName customTabBarSize:(NSInteger)customTabBarSize)
{
    NSDictionary *initialProps = @{@"componentId": moduleName};
    
    dispatch_async(dispatch_get_main_queue(), ^{
        RCTBridge *_brige = [ReactNativeNavigation getBridge];
        RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:_brige moduleName:moduleName initialProperties:initialProps];
        rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
        UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
       
        
        NSDictionary* constants = [Constants getConstants];
        
        // Calcular la altura del marco
        CGFloat bottomTabsHeight = (customTabBarSize > 0) ? customTabBarSize : [constants[@"bottomTabsHeight"] floatValue];
        // Calcular la anchura del marco (el mismo que el ancho de la pantalla)
        CGFloat frameWidth = rootViewController.view.bounds.size.width;
        // Calcular la posición Y para que el marco esté en la parte inferior
        CGFloat frameY = rootViewController.view.bounds.size.height - bottomTabsHeight;
        // Crear el marco con las coordenadas y dimensiones calculadas
        rootView.frame = CGRectMake(0, frameY, frameWidth, bottomTabsHeight);
        rootView.tag = 455;

        
        CGFloat bottomInset = bottomTabsHeight - [constants[@"bottomTabsHeight"] floatValue];
        
        if(customTabBarSize > 0) {
            UIEdgeInsets insets = UIEdgeInsetsMake(0, 0, bottomInset, 0);
            for (UIViewController *vc in  rootViewController.childViewControllers) {
                if(vc != nil){
                    NSLog(@"%@", vc.description);
                    vc.additionalSafeAreaInsets = insets;
                }
            }
        }
     
        [rootViewController.view addSubview:rootView];
    });
  
}

RCT_EXPORT_METHOD(setVisible:(BOOL)visible)
{
    dispatch_async(dispatch_get_main_queue(), ^{
        // Encontrar y ocultar la vista usando el tag
        UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
        UIView *foundView = [rootViewController.view viewWithTag:455];
        if (foundView != nil) {
            foundView.hidden = !visible;
        }
    });
}

@end
