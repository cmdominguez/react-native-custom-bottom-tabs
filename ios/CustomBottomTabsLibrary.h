
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNCustomBottomTabsLibrarySpec.h"

@interface CustomBottomTabsLibrary : NSObject <NativeCustomBottomTabsLibrarySpec>
#else
#import <React/RCTBridgeModule.h>

@interface CustomBottomTabsLibrary : NSObject <RCTBridgeModule>


#endif

@end
