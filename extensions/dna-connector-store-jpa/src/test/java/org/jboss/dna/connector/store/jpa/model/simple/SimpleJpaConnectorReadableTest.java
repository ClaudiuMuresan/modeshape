package org.jboss.dna.connector.store.jpa.model.simple;

import org.jboss.dna.common.statistic.Stopwatch;
import org.jboss.dna.connector.store.jpa.JpaSource;
import org.jboss.dna.graph.ExecutionContext;
import org.jboss.dna.graph.Graph;
import org.jboss.dna.graph.Subgraph;
import org.jboss.dna.graph.connector.RepositoryConnectionFactory;
import org.jboss.dna.graph.connector.RepositoryContext;
import org.jboss.dna.graph.connector.RepositorySource;
import org.jboss.dna.graph.connector.test.ReadableConnectorTest;
import org.jboss.dna.graph.observe.Observer;

public class SimpleJpaConnectorReadableTest extends ReadableConnectorTest {

    /**
     * {@inheritDoc}
     * 
     * @see org.jboss.dna.graph.connector.test.AbstractConnectorTest#setUpSource()
     */
    @Override
    protected RepositorySource setUpSource() {
        // Set the connection properties using the environment defined in the POM files ...
        JpaSource source = new JpaSource();

        source.setModel(JpaSource.Models.SIMPLE.getName());
        source.setName("SimpleJpaSource");
        source.setDialect("org.hibernate.dialect.HSQLDialect");
        source.setDriverClassName("org.hsqldb.jdbcDriver");
        source.setUsername("sa");
        source.setPassword("");
        source.setUrl("jdbc:hsqldb:mem:test");
        source.setShowSql(false);
        source.setAutoGenerateSchema("create");

        source.initialize(new RepositoryContext() {

            private final ExecutionContext context = new ExecutionContext();

            public Subgraph getConfiguration( int depth ) {
                return null;
            }

            public ExecutionContext getExecutionContext() {
                return context;
            }

            public Observer getObserver() {
                return null;
            }

            public RepositoryConnectionFactory getRepositoryConnectionFactory() {
                return null;
            }

        });

        return source;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.jboss.dna.graph.connector.test.AbstractConnectorTest#initializeContent(org.jboss.dna.graph.Graph)
     */
    @Override
    protected void initializeContent( Graph graph ) {
        String initialPath = "";
        int depth = 4;
        int numChildrenPerNode = 4;
        int numPropertiesPerNode = 7;
        Stopwatch sw = new Stopwatch();
        boolean batch = true;
        // graph.createWorkspace().named("default");
        createSubgraph(graph, initialPath, depth, numChildrenPerNode, numPropertiesPerNode, batch, sw, System.out, null);
        graph.createWorkspace().named("other workspace");
        createSubgraph(graph, initialPath, depth, numChildrenPerNode, numPropertiesPerNode, batch, sw, System.out, null);
        graph.useWorkspace("default");
    }

}