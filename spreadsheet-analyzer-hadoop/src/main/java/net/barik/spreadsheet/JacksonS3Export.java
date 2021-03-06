package net.barik.spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import net.barik.spreadsheet.analysis.AnalysisOutput;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonS3Export {
    
	public static void exportItem(AnalysisOutput ao, String exportBucket, String exportKeyPrefix, String relativeFilename) throws IOException {
		byte[] data = serializeModel(ao);
		putObjectS3(exportBucket, exportKeyPrefix + relativeFilename, data);

	}

	public static void exportItem(Set<String> uniqueFormulas, String uniqueFormulasBucket, String uniqueFormulasKeyPrefix, String fileName) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for(String f:uniqueFormulas) {
			os.write(f.getBytes(StandardCharsets.UTF_8));
			os.write("\n".getBytes(StandardCharsets.UTF_8));
		}
		
        putObjectS3(uniqueFormulasBucket, uniqueFormulasKeyPrefix + fileName , os.toByteArray());
	}

	public static String getKeyForURI(String uri) {
        return uri.substring(10, uri.length() - 1);
    }

    public static byte[] serializeModel(Object model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mapper.writeValue(os, model);
        byte[] data = os.toByteArray();

        return data;
    }

    public static void putObjectS3(String bucket, String key, byte[] data) {
        ByteArrayInputStream is = new ByteArrayInputStream(data);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(data.length);

        AmazonS3 s3client = new AmazonS3Client();
        s3client.putObject(bucket, key, is, objectMetadata);
    }


}
