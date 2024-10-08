import { RouteReuseStrategy, ActivatedRouteSnapshot, DetachedRouteHandle } from '@angular/router';

// Dùng để giữ component không bị hủy khi chuyển route giúp tối ưu hiệu xuất
export class CustomReuseStrategy implements RouteReuseStrategy {

  // Lưu trữ các route đã được tách (detached)
  private storedRoutes = new Map<string, DetachedRouteHandle>();

  // Quyết định có nên lưu lại route này không
  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    // Ví dụ: Chỉ lưu route có path là 'posts'
    return route.routeConfig?.path === 'home';
  }

  // Lưu lại route đã bị tách
  store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle | null): void {
    if (route.routeConfig && handle) {
      this.storedRoutes.set(route.routeConfig.path!, handle);
    }
  }

  // Quyết định có nên tải lại route đã được lưu hay không
  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    return !!route.routeConfig && this.storedRoutes.has(route.routeConfig.path!);
  }

  // Lấy lại route đã lưu trữ trước đó
  retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle | null {
    if (!route.routeConfig) {
      return null;
    }
    return this.storedRoutes.get(route.routeConfig.path!) || null;
  }

  // Quyết định có tái sử dụng route này không
  shouldReuseRoute(future: ActivatedRouteSnapshot, current: ActivatedRouteSnapshot): boolean {
    return future.routeConfig === current.routeConfig;
  }
}