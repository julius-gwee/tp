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
    private static boolean initializationFailed = false;

    /**
     * Initializes the JavaFX toolkit for headless testing.
     * This method is idempotent and can be called multiple times safely.
     */
    public static void initToolkit() {
        if (!toolkitInitialized && !initializationFailed) {
            try {
                // Initialize JavaFX toolkit in headless mode
                CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(() -> {
                    toolkitInitialized = true;
                    latch.countDown();
                });

                // Wait for initialization with timeout
                boolean initialized = latch.await(2, TimeUnit.SECONDS);
                if (!initialized) {
                    // Timeout occurred - mark as failed to prevent hanging
                    initializationFailed = true;
                    System.err.println("JavaFX toolkit initialization timed out - UI tests will be skipped");
                } else {
                    toolkitInitialized = true;
                }
            } catch (IllegalStateException e) {
                // Toolkit already initialized
                toolkitInitialized = true;
            } catch (UnsupportedOperationException e) {
                // Platform.startup() not supported in this environment
                // This can happen in some CI environments
                initializationFailed = true;
                System.err.println("JavaFX not supported in this environment - UI tests will be skipped");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                initializationFailed = true;
                System.err.println("JavaFX initialization interrupted - UI tests will be skipped");
            } catch (Exception e) {
                // Catch any other exceptions during toolkit initialization
                initializationFailed = true;
                System.err.println("JavaFX initialization failed: " + e.getMessage());
            }
        }
    }

    /**
     * Checks if JavaFX toolkit is available for testing.
     * @return true if toolkit is initialized and ready, false otherwise
     */
    public static boolean isToolkitAvailable() {
        return toolkitInitialized && !initializationFailed;
    }
}

