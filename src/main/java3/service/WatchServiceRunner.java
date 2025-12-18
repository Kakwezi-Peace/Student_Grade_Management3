package service;

import config.Config;   // ✅ import your own Config class

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceRunner implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path dir = Path.of(Config.IMPORTS_DIR);   // ✅ use Config constant
            Files.createDirectories(dir);
            dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            while (running) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path created = dir.resolve((Path) event.context());
                        System.out.println("[Watcher] New file detected: " + created);
                        // TODO: trigger import logic
                    }
                }
                key.reset();
            }
        } catch (InterruptedException | IOException e) {
            System.err.println("[Watcher] " + e.getMessage());
        }
    }

    public void stop() { running = false; }
}
