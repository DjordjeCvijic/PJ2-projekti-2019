package model;

import applications.LoggerService;
import controller.MainApplicationController;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;

public class JavaDirectoryChangeListener extends Thread {

    private Path directoryPath;
    private String name;

    public JavaDirectoryChangeListener(Path dir, String n) {
        directoryPath = dir;
        name = n;
    }

    @Override
    public void run() {
        try {
            WatchService watchService = directoryPath.getFileSystem().newWatchService();
            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            //Start infinite loop to watch changes on the directory
            while (true) {

                WatchKey watchKey = watchService.take();

                // poll for file system events on the WatchKey
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    //Calling method
                    takeActionOnChangeEvent(event);
                }

                //Break out of the loop if watch directory got deleted
                if (!watchKey.reset()) {
                    watchKey.cancel();
                    watchService.close();
                    //Break out from the loop
                    break;
                }
            }

        } catch (InterruptedException e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
            return;
        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
            return;
        }

    }

    private void takeActionOnChangeEvent(WatchEvent<?> event) {

        Kind<?> kind = event.kind();

        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            Path entryCreated = (Path) event.context();

            if (name.equals("events")) {

                MainApplicationController.setInfoText("Worning enemy aircraft");
            }
            if (name.equals("alert")) {

                MainApplicationController.newCrash();
            }

        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            Path entryDeleted = (Path) event.context();

        } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            Path entryModified = (Path) event.context();

        }
    }
}
