package org.modeshape.connector.svn;

import org.modeshape.graph.Graph;
import org.modeshape.graph.connector.RepositorySource;
import org.modeshape.graph.connector.test.NotWritableConnectorTest;
import org.junit.BeforeClass;

public class SvnRepositoryConnectorNotWritableTest extends NotWritableConnectorTest {

    private static String url;

    @BeforeClass
    public static void beforeAny() throws Exception {
        url = SvnConnectorTestUtil.createTestRepoURL();

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.modeshape.graph.connector.test.AbstractConnectorTest#setUpSource()
     */
    @Override
    protected RepositorySource setUpSource() throws Exception {
        String[] predefinedWorkspaceNames = new String[] {"trunk", "tags"};
        SvnRepositorySource source = new SvnRepositorySource();
        source.setName("Test Repository");
        source.setUsername("sp");
        source.setPassword("");
        source.setRepositoryRootUrl(url);
        source.setPredefinedWorkspaceNames(predefinedWorkspaceNames);
        source.setDefaultWorkspaceName(predefinedWorkspaceNames[0]);
        source.setCreatingWorkspacesAllowed(false);
        
        return source;
    }


    /**
     * {@inheritDoc}
     * 
     * @see org.modeshape.graph.connector.test.AbstractConnectorTest#initializeContent(org.modeshape.graph.Graph)
     */
    @Override
    protected void initializeContent( Graph graph ) {
        // No need to initialize any content ...
    }
}
