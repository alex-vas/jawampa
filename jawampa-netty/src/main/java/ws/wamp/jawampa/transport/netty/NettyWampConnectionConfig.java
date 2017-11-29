package ws.wamp.jawampa.transport.netty;

import io.netty.handler.ssl.SslContext;
import ws.wamp.jawampa.connection.IWampClientConnectionConfig;

public class NettyWampConnectionConfig implements IWampClientConnectionConfig {

    static final int DEFAULT_MAX_FRAME_PAYLOAD_LENGTH = 65535;

    final SslContext sslContext;
    final int maxFramePayloadLength;

    final int pingPeriodSeconds;
    final int pingTimeoutSeconds;
    
    NettyWampConnectionConfig(SslContext sslContext, int maxFramePayloadLength, int pingPeriodSeconds, int pingTimeoutSeconds) {
        this.sslContext = sslContext;
        this.maxFramePayloadLength = maxFramePayloadLength;
        this.pingPeriodSeconds = pingPeriodSeconds;  
        this.pingTimeoutSeconds = pingTimeoutSeconds;
    }

    /**
     * the SslContext which will be used to create Ssl connections to the WAMP
     * router. If this is set to null a default (unsecure) SSL client context will be created
     * and used. 
     */
    public SslContext sslContext() {
        return sslContext;
    }

    public int getMaxFramePayloadLength() {
        return maxFramePayloadLength;
    }

    public int getPingPeriodSeconds() {
        return pingPeriodSeconds;
    }
    
    public int getPingTimeoutSeconds() {
        return pingTimeoutSeconds;
    }
    
    /**
     * Builder class that must be used to create a {@link NettyWampConnectionConfig}
     * instance.
     */
    public static class Builder {

        SslContext sslContext;
        int maxFramePayloadLength = DEFAULT_MAX_FRAME_PAYLOAD_LENGTH;
        int pingPeriodSeconds;
        int pingTimeoutSeconds;
        
        /**
         * Allows to set the SslContext which will be used to create Ssl connections to the WAMP
         * router. If this is set to null a default (unsecure) SSL client context will be created
         * and used. 
         * @param sslContext The SslContext that will be used for SSL connections.
         * @return The {@link Builder} object
         */
        public Builder withSslContext(SslContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        public Builder withMaxFramePayloadLength(int maxFramePayloadLength){
            if ( maxFramePayloadLength <= 0 ){
                throw new IllegalArgumentException("maxFramePayloadLength parameter cannot be negative");
            }
            this.maxFramePayloadLength = maxFramePayloadLength;
            return this;
        }

        public Builder withKeepAlive(int pingPeriodSeconds, int pingTimeoutSeconds) {
            if (pingPeriodSeconds <= 0) {
                throw new IllegalArgumentException("pingPeriodSeconds parameter cannot be negative");
            }
            if (pingTimeoutSeconds <= 0) {
                throw new IllegalArgumentException("pingTimeoutSeconds parameter cannot be negative");
            }
            this.pingPeriodSeconds = pingPeriodSeconds;  
            this.pingTimeoutSeconds = pingTimeoutSeconds;
            return this;
        }

        public NettyWampConnectionConfig build() {
            return new NettyWampConnectionConfig(sslContext, maxFramePayloadLength, pingPeriodSeconds, pingTimeoutSeconds);
        }
    }
}
