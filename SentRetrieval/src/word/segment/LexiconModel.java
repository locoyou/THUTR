package word.segment;

import java.io.*;
import java.util.*;

import bean.ConfiFile;

public class LexiconModel {

	int labelSize;
	int featureSize;
	int[][] fealabWeight;
	int[][] labelWeight;
	HashMap<String, Integer> indexLabelDic;
	HashMap<String, Integer> indexFeatureDic;
	List<String> labelDic;
	List<String> featureDic;
	// lexiconPath need to be revised
	static final String labelModel = "3in1_label_info.txt";
	static final String featureModel = "3in1_index.txt";
	static final String weightModel = "3in1_model.bin";

	public LexiconModel() {
		try {
			// initialize
			labelDic = new ArrayList<String>();
			featureDic = new ArrayList<String>();
			indexLabelDic = new HashMap<String, Integer>();
			indexFeatureDic = new HashMap<String, Integer>();

			File dataFile = new File(ConfiFile.WordSegmentModel+weightModel);
			File labelFile = new File(ConfiFile.WordSegmentModel+labelModel);
			File featureFile = new File(ConfiFile.WordSegmentModel+featureModel);

			BufferedInputStream dataIn = new BufferedInputStream(
					new FileInputStream(dataFile));
			BufferedReader labelIn = new BufferedReader(new InputStreamReader(
					new FileInputStream(labelFile), "UTF-8"));
			BufferedReader featureIn = new BufferedReader(
					new InputStreamReader(new FileInputStream(featureFile),
							"UTF-8"));

			String tmpString;
			byte[] data = new byte[4];
			int cnt = 0;
			// load label

			while ((tmpString = labelIn.readLine()) != null) {
				labelDic.add(tmpString);
				indexLabelDic.put(tmpString, cnt);
				cnt++;
			}

			// load feature
			cnt = 0;
			while ((tmpString = featureIn.readLine()) != null) {
				featureDic.add(tmpString);
				indexFeatureDic.put(tmpString, cnt);
				cnt++;
			}

			// load feature-label weights
			dataIn.read(data);
			this.labelSize = convByte2Int(data);
			dataIn.read(data);
			this.featureSize = convByte2Int(data);

			this.labelWeight = new int[this.labelSize][this.labelSize];
			this.fealabWeight = new int[this.featureSize][this.labelSize];
			// System.out.println("labelSize: " + this.labelSize);
			// System.out.println("featureSize: " + this.featureSize);

			for (int i = 0; i < this.labelSize; i++)
				for (int j = 0; j < this.labelSize; j++) {
					dataIn.read(data);
					this.labelWeight[i][j] = convByte2Int(data);
				}
			for (int i = 0; i < this.featureSize; i++)
				for (int j = 0; j < this.labelSize; j++) {
					dataIn.read(data);
					this.fealabWeight[i][j] = convByte2Int(data);
				}
			dataIn.close();
			labelIn.close();
			featureIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	private int convByte2Int(byte[] data) {
		int iOut = 0;
		for (int i = 0; i < 4; i++)
			iOut += (data[i] & 0xFF) << (8 * i);
		return iOut;
	}

	public static void main(String[] args) {
		LexiconModel lexicon = new LexiconModel();
	}
}