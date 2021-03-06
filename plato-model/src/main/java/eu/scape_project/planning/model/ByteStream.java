/*******************************************************************************
 * Copyright 2006 - 2012 Vienna University of Technology,  
 * Department of Software Technology and Interactive Systems, IFS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This work originates from the Planets project, co-funded by the European Union under the Sixth Framework Programme.
 ******************************************************************************/
package eu.scape_project.planning.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ByteStream implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2177546307805229793L;

    /**
     * this is stored redundantly here, of course it could be calculated every time 
     * by accessing the byte[] - but we want to cache this in here to not access the
     * db. This is not exported/imported to/from XML, but stored in the DB.
     * @see #setData(byte[])
     */
    private long size;
    
//    @Transient
//    private String dataInMB;

//    @Transient
//    private boolean dataExistent;
    
    /**
     * We refactored that from private byte[] data. The problem is that
     * LAZY fetching for single columns doesn't really work.
     * For detailed information cf. 
     * http://docs.jboss.org/hibernate/stable/core/reference/en/html_single/#performance-fetching-lazyproperties
     */
    //@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @Transient
    private RealByteStream realByteStream = new RealByteStream();
    
    @Id
    @GeneratedValue
    private int id;
    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
    
    
    public long getSize() {
        return size;
    }


    public void setSize(long size) {
        this.size = size;
    }


    public boolean isDataExistent() {
        return (size > 0);
    }
    
    public byte[] getData() {
        return realByteStream.getData();
    }
    
    /**
     * Releases the data in the bytestream so memory can be freed
     * - does not reset the size  
     */
    public void releaseData(){
        long realSize = this.size;
        setData(null);
        this.size = realSize;
    }

    public void setData(byte[] data) {
        realByteStream.setData(data);
        setSize((data==null)? 0 : data.length);
    }

    public ByteStream clone() {
        ByteStream clone = new ByteStream();
        clone.setRealByteStream(realByteStream.clone());
        clone.setSize(getSize());
        return clone;
    }

    public RealByteStream getRealByteStream() {
        return realByteStream;
    }

    public void setRealByteStream(RealByteStream realByteStream) {
        this.realByteStream = realByteStream;
    }
}
