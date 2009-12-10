/*
 * JBoss DNA (http://www.jboss.org/dna)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors.
 *
 * JBoss DNA is free software. Unless otherwise indicated, all code in JBoss DNA
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * JBoss DNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.dna.search.lucene;

import net.jcip.annotations.Immutable;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.jboss.dna.graph.ExecutionContext;
import org.jboss.dna.graph.search.SearchEngineWorkspace;

/**
 * The {@link SearchEngineWorkspace} implementation for the {@link LuceneSearchEngine}.
 */
@Immutable
public class LuceneSearchWorkspace implements SearchEngineWorkspace {

    protected static final String PATHS_INDEX_NAME = "paths";
    protected static final String CONTENT_INDEX_NAME = "content";

    /**
     * Given the name of a property field of the form "&lt;namespace>:&lt;local>" (where &lt;namespace> can be zero-length), this
     * provider also stores the value(s) for free-text searching in a field named ":ft:&lt;namespace>:&lt;local>". Thus, even if
     * the namespace is zero-length, the free-text search field will be named ":ft::&lt;local>" and will not clash with any other
     * property name.
     */
    protected static final String FULL_TEXT_PREFIX = ":ft:";

    /**
     * This index stores only these fields, so we can use the most obvious names and not worry about clashes.
     */
    static class PathIndex {
        public static final String PATH = "pth";
        public static final String NODE_NAME = "nam";
        public static final String LOCAL_NAME = "loc";
        public static final String SNS_INDEX = "sns";
        public static final String LOCATION_ID_PROPERTIES = "idp";
        public static final String ID = ContentIndex.ID;
        public static final String DEPTH = "dep";
    }

    /**
     * This index stores these two fields <i>plus</i> all properties. Therefore, we have to worry about name clashes, which is why
     * these field names are prefixed with '::', which is something that does appear in property names as they are serialized.
     */
    static class ContentIndex {
        public static final String ID = "::id";
        public static final String FULL_TEXT = "::fts";
    }

    private final String workspaceName;
    protected final IndexRules rules;
    private final LuceneConfiguration configuration;
    protected final Directory pathDirectory;
    protected final Directory contentDirectory;
    protected final Analyzer analyzer;

    protected LuceneSearchWorkspace( String workspaceName,
                                     LuceneConfiguration configuration,
                                     IndexRules rules,
                                     Analyzer analyzer,
                                     boolean overwrite ) {
        assert workspaceName != null;
        assert configuration != null;
        this.workspaceName = workspaceName;
        this.analyzer = analyzer != null ? analyzer : new StandardAnalyzer(Version.LUCENE_30);
        this.rules = rules != null ? rules : LuceneSearchEngine.DEFAULT_RULES;
        this.configuration = configuration;
        this.pathDirectory = this.configuration.getDirectory(workspaceName, PATHS_INDEX_NAME);
        this.contentDirectory = this.configuration.getDirectory(workspaceName, CONTENT_INDEX_NAME);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.jboss.dna.graph.search.SearchEngineWorkspace#getWorkspaceName()
     */
    public String getWorkspaceName() {
        return workspaceName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.jboss.dna.graph.search.SearchEngineWorkspace#destroy(org.jboss.dna.graph.ExecutionContext)
     */
    public void destroy( ExecutionContext context ) {
        configuration.destroyDirectory(workspaceName, PATHS_INDEX_NAME);
        configuration.destroyDirectory(workspaceName, CONTENT_INDEX_NAME);
    }

    /**
     * @return rules
     */
    public IndexRules getRules() {
        return rules;
    }
}