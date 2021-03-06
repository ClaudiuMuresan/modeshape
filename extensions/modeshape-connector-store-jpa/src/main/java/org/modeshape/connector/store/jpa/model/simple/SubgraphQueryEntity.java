/*
 * ModeShape (http://www.modeshape.org)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors. 
 *
 * ModeShape is free software. Unless otherwise indicated, all code in ModeShape
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * ModeShape is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.modeshape.connector.store.jpa.model.simple;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a temporary working area for a query that retrieves the nodes in a subgraph.
 */
@Entity( name = "MODE_SUBGRAPH_QUERIES" )
public class SubgraphQueryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "ID", updatable = false )
    private Long id;

    @Column( name = "WORKSPACE_ID", nullable = false )
    private Long workspaceId;

    @Column( name = "ROOT_UUID", updatable = false, nullable = false, length = 36 )
    private String rootUuid;

    public SubgraphQueryEntity( Long workspaceId,
                                String rootUuid ) {
        this.rootUuid = rootUuid;
        this.workspaceId = workspaceId;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return rootUuid
     */
    public String getRootUuid() {
        return rootUuid;
    }

    /**
     * @return workspaceId
     */
    public Long getWorkspaceId() {
        return workspaceId;
    }
}
