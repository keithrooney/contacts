package org.contacts.data;

import org.contacts.concurrent.locks.FileLockService;
import org.contacts.data.filesystem.DistributedFileSystemContactsDAO;
import org.contacts.data.filesystem.FileSystemProperties;
import org.contacts.data.filesystem.LocalFileSystemContactsDAO;
import org.contacts.data.mongodb.MongoContactsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
@EnableConfigurationProperties({FileSystemProperties.class})
public class ContactsDAOAutoConfiguration {

	@Configuration
	@ConditionalOnProperty(prefix="spring.data.mongodb", name="host", matchIfMissing=false)
	@Import(org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class)
	public class MongoAutoConfiguration extends AbstractMongoConfiguration {

		@Autowired
		private MongoProperties properties;

		@Bean
		public ContactsDAO mongoContactsDAO() {
			return new MongoContactsDAO();
		}

		@Override
		public MongoClient mongoClient() {
			return new MongoClient(properties.getHost(), properties.getPort());
		}

		@Override
		protected String getDatabaseName() {
			return properties.getDatabase();
		}
		
	}
	
	@Configuration
	@ConditionalOnBean(ContactsDAO.class)
	@AutoConfigureAfter(MongoAutoConfiguration.class)
	@ConditionalOnProperty(prefix="spring.data.filesystem", name="type", havingValue="local", matchIfMissing=true)
	public static class JVMFileSystemAutoConfiguration {
		
		@Autowired
		private FileSystemProperties properties;
		
		@Bean
		public ContactsDAO jvmFileSystemContactsDAO() {
			return new LocalFileSystemContactsDAO(properties.getStorage());
		}
		
	}

	@Configuration
	@ConditionalOnProperty(prefix="spring.data.filesystem", name="type", havingValue="distributed", matchIfMissing=false)
	public static class DistributedFileSystemAutoConfiguration {
		
		@Autowired
		private FileSystemProperties properties;
		
		@Bean
		public ContactsDAO distributedFileSystemContactsDAO() {
			return new DistributedFileSystemContactsDAO(properties.getStorage());
		}
		
		@Bean
		public FileLockService fileLockService() {
			return new FileLockService(properties.getLocks(), properties.getLease());
		}
		
	}

	
}
