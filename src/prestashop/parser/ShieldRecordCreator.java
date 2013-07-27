package prestashop.parser;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;

import prestashop.interfaces.Record;
import prestashop.interfaces.RecordCreator;
import prestashop.utils.ProductRecord;

public class ShieldRecordCreator extends RecordCreator {

	private final static int PRODUCTS_LIST_START_COLUMN_INDEX = 10;
    private final static int BRAND_COLUMN_INDEX = 0;
    private final static int MODEL_COLUMN_INDEX = 1;
    private final static int ENGINE_COLUMN_INDEX = 2;
    private final static int COMMENT_COLUMN_INDEX = 3;
    private final static int HEIGHT_COLUMN_INDEX = 6;
    private final static int WIDTH_COLUMN_INDEX = 7;
    private final static int DEPTH_COLUMN_INDEX = 8;
    private final static int WEIGHT_COLUMN_INDEX = 9;
    private final static String SPLITTING_STRING = ";";
    
	@Override
	public Record create(Row row) {
		ProductRecord record = new ProductRecord();
		int howmany = row.getPhysicalNumberOfCells();
		if (howmany >= PRODUCTS_LIST_START_COLUMN_INDEX) {
			record.setBrand(getStringValue(row.getCell(BRAND_COLUMN_INDEX)));
			record.setModel(getStringValue(row.getCell(MODEL_COLUMN_INDEX)));
			record.setEngine(getStringValue(row.getCell(ENGINE_COLUMN_INDEX)));
			record.setComment(getStringValue(row.getCell(COMMENT_COLUMN_INDEX)));
			record.setWidth(getStringValue(row.getCell(WIDTH_COLUMN_INDEX)));
			record.setHeight(getStringValue(row.getCell(HEIGHT_COLUMN_INDEX)));
			record.setDepth(getStringValue(row.getCell(DEPTH_COLUMN_INDEX)));
			record.setWeight(getStringValue(row.getCell(WEIGHT_COLUMN_INDEX)));
			record.setProducts(parseProductsList(row, PRODUCTS_LIST_START_COLUMN_INDEX, howmany));			
		}
		return record;
	}
	
	private HashMap<String, String[]> parseProductsList(Row row, int startIndex, int endIndex){
		HashMap<String, String[]> products = new HashMap<String, String[]>(); 
		for (int i = startIndex; i < endIndex; ++i) {
			String values[] = null;
			String ids = getStringValue(row.getCell(i));
			if (ids != null) {
				values = ids.split(SPLITTING_STRING);
			    products.put(getStringValue(row.getSheet().getRow(0).getCell(i)), values);
			}
		}
		return products;
	}
}
