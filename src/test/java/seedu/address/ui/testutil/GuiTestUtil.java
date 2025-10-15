package seedu.address.ui.testutil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * Utility class for JavaFX GUI tests.
 * Handles JavaFX toolkit initialization for headless testing.
 */
public class GuiTestUtil {
    private static boolean toolkitInitialized = false;

    /**
     * Initializes the JavaFX toolkit for headless testing.
     * This method is idempotent and can be called multiple times safely.
     */
    public static void initToolkit() {
        if (!toolkitInitialized) {
            try {
                // Initialize JavaFX toolkit in headless mode
                CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(() -> {
                    toolkitInitialized = true;
                    latch.countDown();
                });
                latch.await(5, TimeUnit.SECONDS);
            } catch (IllegalStateException e) {
                // Toolkit already initialized
                toolkitInitialized = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Failed to initialize JavaFX toolkit", e);
            }
        }
    }
}

