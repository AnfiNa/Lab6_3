package org.example.server;


import java.util.logging.Logger;


import org.example.common.Requests.Request;
import org.example.common.Responses.Response;
import org.example.common.studyGroupClasses.StudyGroup;
import org.example.server.serverModules.AcceptConnectionModule;
import org.example.server.serverModules.CommandExecutionModule;
import org.example.server.serverModules.RequestReadModule;
import org.example.server.serverModules.SendResponseModule;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    private final int saveInterval = 300;

    private static final Logger logger = Logger.getLogger("server");

    /**
     * Обработчик команд
     */
    private final CommandExecutionModule executor;

    /**
     * Менеджер коллекции
     */
    private final CollectionManager manager;

    private final AcceptConnectionModule acceptConnectionModule;

    private final RequestReadModule requestReadModule;

    private final SendResponseModule sendResponseModule;

    private final int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 1234;


    /**
     * Конструктор приложения
     */
    public Server() {
        this.manager = new CollectionManager(new TreeSet<StudyGroup>(), logger);
        this.executor = new CommandExecutionModule(manager);
        this.acceptConnectionModule = new AcceptConnectionModule(logger);
        this.requestReadModule = new RequestReadModule(logger);
        this.sendResponseModule = new SendResponseModule();
    }


    public void run() throws IOException, ClassNotFoundException {
        this.manager.loadInitialCollection();
        InetSocketAddress address = new InetSocketAddress(port); // создаем адрес сокета (IP-адрес и порт)

        ServerSocketChannel channel = ServerSocketChannel.open(); // канал для сервера, который слушает порты и создает сокеты для клиентов
        channel.bind(address); // теперь канал слушает по определенному сокету
        channel.configureBlocking(false); // неблокирующий режим

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        startSavingTask(saveInterval);
        logger.info("Server is listening on port %s".formatted(port));

        try {
            Response response = new Response("Странная команда");
            while (true) {
                selector.select(); // количество ключей, чьи каналы готовы к операции. БЛОКИРУЕТ, ПОКА НЕ БУДЕТ КЛЮЧЕЙ
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // получаем список ключей от каналов, готовых к работеwhile (iter.hasNext()) {
                Iterator<SelectionKey> iter = selectedKeys.iterator(); // получаем итератор ключей
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    try {
                        if (key.isAcceptable()) {
                            acceptConnectionModule.handleAccept(key);
                        } else if (key.isReadable()) {
                            logger.info("Getting request...");
                            response = executeCommand(requestReadModule.handleRead(key));
                        } else if (key.isWritable()) {
                            logger.info("Sending response...");
                            sendResponseModule.handleWrite(key,response);
                        }
                    } catch (IOException e) {
                        logger.info("Client disconnected");
                        key.cancel();
                    }
                }
            }
        } catch (IOException e) {
            logger.warning("Client is disconnected");

        }
        this.manager.saveCollection();
    }

    public void startSavingTask(int intervalInSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable saveTask = this.manager::saveCollection;

        // Schedule the save task to run every n seconds
        scheduler.scheduleAtFixedRate(saveTask, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public Response executeCommand(Request request) {
        try {
            return new Response(executor.processCommand(request.getCommand(), request.getArgs(), request.getStudyGroup()));
        } catch (NullPointerException e) {
            return new Response("Bad");
        }
    }
}

