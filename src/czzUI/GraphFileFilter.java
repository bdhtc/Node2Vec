package czzUI;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * ͼ�ļ�������
 * @author CZZ*/
public class GraphFileFilter extends FileFilter{

	/**
	 * ���˺���*/
	@Override
	public boolean accept(File file) {
		boolean ret = true;
		if(file.isDirectory())  
            ret = true;  
        else  
        {  
            String fileName = file.getName();  
            int index = fileName.lastIndexOf('.');
    		if (index > 0 && index < fileName.length() - 1) {
    			String extension = fileName.substring(index + 1).toLowerCase();			//�ļ���չ�������ִ�Сд��
    			if (extension.equals("edgelist")) ret = true;
    		}
            else ret = false;  
        }
		return ret;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "�ڽӱ��ļ�(*.edgelist)";
	}

}
