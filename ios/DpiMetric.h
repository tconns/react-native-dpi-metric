
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNDpiMetricSpec.h"

@interface DpiMetric : NSObject <NativeDpiMetricSpec>
#else
#import <React/RCTBridgeModule.h>

@interface DpiMetric : NSObject <RCTBridgeModule>
#endif

@end
