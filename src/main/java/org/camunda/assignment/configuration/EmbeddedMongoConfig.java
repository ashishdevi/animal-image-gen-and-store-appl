//package org.camunda.assignment.configuration;
//
//import com.mongodb.client.MongoClient;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodProcess;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.MongodConfig;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//
//import java.io.IOException;
//
//import de.flapdoodle.embed.mongo.Command;
//import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
//
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.config.io.ProcessOutput;
//import de.flapdoodle.embed.process.runtime.Network;
//@Configuration
//public class EmbeddedMongoConfig extends AbstractMongoClientConfiguration {
//    /**
//     * Return the name of the database to connect to.
//     *
//     * @return must not be {@literal null}.
//     */
//    @Configuration
//    public class EmbeddedMongoConfiguration extends AbstractMongoClientConfiguration {
//
//        @Override
//        protected String getDatabaseName() {
//            return "test"; // Your preferred database name
//        }
//
//        @Override
//        @Bean(destroyMethod = "stop")
//        public com.mongodb.client.MongoClient mongoClient() throws Exception {
//            IMongodConfig mongodConfig = new MongodConfigBuilder()
//                    .version(Version.Main.PRODUCTION)
//                    .net(new de.flapdoodle.embed.mongo.config.Net("localhost", 27017, Network.localhostIsIPv6()))
//                    .cmdOptions(Command.MongoD)
//                    .build();
//
//            return new de.flapdoodle.embed.mongo.MongodExecutable(mongodConfig, new de.flapdoodle.embed.process.runtime.Starter(),
//                    new de.flapdoodle.embed.process.store.ExtractedArtifactStoreBuilder()
//                            .defaults(Command.MongoD)
//                            .download(new de.flapdoodle.embed.process.extract.UserTempHome())
//                            .build())
//                    .start()
//                    .getMongoClient(new ConnectionString("mongodb://localhost:" + mongodConfig.net().getPort()));
//        }
//    }
//
//}