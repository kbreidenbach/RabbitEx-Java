package me.breidenbach.rabbitex.connection;


import com.rabbitmq.client.ConnectionFactory;
import me.breidenbach.rabbitex.RabbitEx;
import org.springframework.stereotype.Component;

/**
 * User: Kevin E. Breidenbach
 * Date: 11/1/13
 * Time: 4:08 PM
 * © 2013 Kevin E. Breidenbach
 */
@Component
public class RabbitConnectionFactory {

    private final RabbitConnectionCache cache = new RabbitConnectionCache();

    public RabbitEx rabbitConnection(final String host, final int port) throws RabbitConnectionException {
        return rabbitConnection (host, port, "", "", "");
    }

    public RabbitEx rabbitConnection(final String host, final int port, final String virtualHost,
                                     final String username, final String password) throws RabbitConnectionException {

        RabbitConnection connection = cache.retrieve(host, port, virtualHost, username);
        if (connection == null || connection.isClosed()) {
            ConnectionFactory factory = createConnectionFactory(host, port, virtualHost, username, password);
            connection = new RabbitConnection(cache, factory);
            cache.cache(host, port, virtualHost, username, connection);
        }
        return connection;
    }

    private ConnectionFactory createConnectionFactory(final String host, final int port, final String virtualHost,
                                                      final String username, final String password) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        if (virtualHost != null && !virtualHost.isEmpty()) {
            factory.setVirtualHost(virtualHost);
        }
        if (username != null && !username.isEmpty()) {
            factory.setUsername(username);
        }
        if (password != null && !password.isEmpty()) {
            factory.setPassword(password);
        }
        return factory;
    }
}
