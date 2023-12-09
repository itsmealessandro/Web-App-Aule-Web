package data.domain;

import framework.data.DataItem;

/**
 *
 * @author alessandro
 */
public interface Amministratore extends DataItem<Integer>{
    
    public String getUsername();
    
    public String getPassword();
    
    public void setUsername(String username);
    
    public void setPassword(String password);
    
}
