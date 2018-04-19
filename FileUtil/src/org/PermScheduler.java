package org;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;


public class PermScheduler {
	public void setPermission2(File file) throws IOException{
	    Set<PosixFilePermission> perms = new HashSet<>();
	    perms.add(PosixFilePermission.OWNER_READ);
	    perms.add(PosixFilePermission.OWNER_WRITE);

	    perms.add(PosixFilePermission.OTHERS_READ);
	    perms.add(PosixFilePermission.OTHERS_WRITE);

	    perms.add(PosixFilePermission.GROUP_READ);
	    perms.add(PosixFilePermission.GROUP_WRITE);

	    Files.setPosixFilePermissions(file.toPath(), perms);
	}
	
	public void setPermission(File file) throws IOException {
        // changing the file permissions
        file.setExecutable(false);
        file.setReadable(true);
        file.setWritable(true);

        file.setExecutable(false, false);
        file.setReadable(true, true);
        file.setWritable(true, true);
	}
	
    public static void main(String[] args)
    {
    	boolean posix = false;
    	
    	String dir = "/edinew/newtrans/cadata/FF_SHIP/in/";
    	if (args.length > 0) dir = args[0];
    	if (args.length > 1) posix = true;
    	
		File folder = null;
		File[] listOfFiles = null;
		PermScheduler perm = new PermScheduler();
		
    	try {
    		folder = new File(dir);
    		if (!folder.exists()) {
				System.out.println("No such directory exists: " + dir);
				System.exit(0);
    		}
    		System.out.println("Polling the folder: " + dir);
			
	    	while (true) {
				listOfFiles = folder.listFiles();
				if (listOfFiles == null) {
					System.out.println("No such directory exists: " + dir);
					System.exit(0);
				}
				for (File file : listOfFiles) {
				    if (file.isFile()) {
				        if (file.getName().startsWith("AP")) {
				        	if (posix)
				        		perm.setPermission2(file);
				        	else
				        		perm.setPermission(file);
				        	//System.out.println("@Permisstion Changed: " + file.getName());
				        }
				    }
				}
	    		
	    		try {
	    		    Thread.sleep(1000);                 //1000 milliseconds is one second.
	    		} catch(InterruptedException ex) {
	    		    Thread.currentThread().interrupt();
	    		}
	    	}
	    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		folder = null;
    		listOfFiles = null;
    		perm = null;
    	}
    }
}
