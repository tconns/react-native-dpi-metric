#import "DpiMetric.h"

@implementation DpiMetric
RCT_EXPORT_MODULE()

- (NSDictionary *)constantsToExport
{
    return @{
        @"isTablet": @([self isTablet]),
        @"deviceInch": @([self deviceInch]),
        @"dpi": @([self dpi])
    };
}

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (float) dpi
{
    float scale = [[UIScreen mainScreen] scale];
    float ddpi = 0;

     // Xác định PPI dựa trên kích thước màn hình và thiết bị
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
        ddpi = 132 * scale; // iPad thường
    } else if ([UIScreen mainScreen].bounds.size.height >= 812) {
        ddpi = 458; // iPhone với màn hình Super Retina (iPhone X, 11 Pro)
    } else {
        ddpi = 326 * scale; // iPhone Retina thông thường (iPhone 6, 7, 8, SE)
    }

    return ddpi;
}

- (BOOL) isTablet
{
    // TODO: Implement some actually useful functionality
    if ( UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad )
    {
        return YES; /* Device is iPad */
    }else{
        return NO;
    }

}

- (float) deviceInch
{
    float scale = [[UIScreen mainScreen] scale];

    NSInteger width = [[UIScreen mainScreen] bounds].size.width;
    NSInteger height = [[UIScreen mainScreen] bounds].size.height;

    NSInteger screenHeight = MAX(width, height);

    switch (screenHeight) {
        case 240:
            return 3.5;
        case 480:
            return 3.5;
        case 568:
            return 4;
        case 667:
            return scale == 3.0 ? 5.5 : 4.7;
        case 736:
            return 5.5;
        case 812:
            return 5.4;
        case 844:
            return 6.1;
        case 896:
            return 6.5;
        case 926:
            return 6.7;
        case 1024:
            return 9.7;
        case 1080:
            return 10.2;
        case 1112:
            return 10.5;
        case 1180:
            return 10.9;
        case 1194:
            return 11;
        case 1366:
            return 12.9;
        default:
            return 0;
    }
}

// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_EXPORT_METHOD(multiply:(double)a
                  b:(double)b
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    NSNumber *result = @(a * b);

    resolve(result);
}


@end
