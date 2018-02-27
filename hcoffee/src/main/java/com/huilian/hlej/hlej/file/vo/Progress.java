    package com.huilian.hlej.hlej.file.vo;    
        
    import java.io.Serializable;    
        
    public class Progress implements Serializable {    
    	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private long bytesRead;
    	    private long contentLength;
    	    private long items;
			public long getBytesRead() {
				return bytesRead;
			}
			public void setBytesRead(long bytesRead) {
				this.bytesRead = bytesRead;
			}
			public long getContentLength() {
				return contentLength;
			}
			public void setContentLength(long contentLength) {
				this.contentLength = contentLength;
			}
			public long getItems() {
				return items;
			}
			public void setItems(long items) {
				this.items = items;
			}
			@Override
			public String toString() {
				return "Progress [bytesRead=" + bytesRead + ", contentLength=" + contentLength + ", items=" + items
						+ "]";
			} 
    	    
       
            
    }  