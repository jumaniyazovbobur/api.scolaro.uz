package api.scolaro.uz.util;

public class PaginationUtil {
    public static int page(int page) {
        return page == 0 ? 0 : page - 1;
    }
}
