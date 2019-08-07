package model;

import controller.MainApplicationController;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class JavaDirectoryChangeListener extends Thread {

    private static Path directoryPath;
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
                    System.out.println("Watch directory got deleted. Stop watching it.");
                    //Break out from the loop
                    break;
                }
            }

        } catch (InterruptedException interruptedException) {
            System.out.println("Thread got interrupted:" + interruptedException);
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

    }

    private void takeActionOnChangeEvent(WatchEvent<?> event) {

        Kind<?> kind = event.kind();

        if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            Path entryCreated = (Path) event.context();
            System.out.println("New entry created:" + entryCreated);
            if (entryCreated.endsWith("r")) {
                System.out.println("ne smije");
                MainApplicationController.setInfoText("Worning enemy aircraft");
            }else {
                MainApplicationController.newCrach();
            }

        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            Path entryDeleted = (Path) event.context();
            System.out.println("Exissting entry deleted:" + entryDeleted);
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            Path entryModified = (Path) event.context();
            System.out.println("Existing entry modified:" + entryModified);
        }
    }
}
