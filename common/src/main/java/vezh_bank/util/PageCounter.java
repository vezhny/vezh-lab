package vezh_bank.util;

public class PageCounter {
    private static Logger logger = Logger.getLogger(PageCounter.class);

    public static int getPagesCount(int rowsOnPage, int numberOfItems) {
        int pagesCount = (int) Math.ceil((double) numberOfItems / rowsOnPage);
        logger.info("Pages count: " + pagesCount);
        return pagesCount;
    }
}
