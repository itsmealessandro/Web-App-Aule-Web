package data.proxy;

import data.domainImpl.AmministratoreImpl;
import framework.data.DataItemProxy;
import framework.data.DataLayer;

/**
 *
 * @author alessandro
 */
public class AmministratoreProxy extends AmministratoreImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public AmministratoreProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.modified = false;
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        this.modified = true;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.modified = true;
    }

    //METODI DEL PROXY
    //PROXY-ONLY METHODS
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
}
