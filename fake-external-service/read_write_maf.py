import pandas as pd

def read_maf(maf_file_name, maf_fields):
    maf = pd.read_csv(maf_file_name, sep='\t', usecols=maf_fields, comment='#')
    return maf

def write_maf(maf, output_file_name):
    maf.to_csv(output_file_name, sep='\t', index=False)
    
    
def main():
    maf_input_file_name = "./input/GBM_MEMo.maf"
    maf_output_file_name = "./input/GBM_MEMo_filtered.maf"

    
    maf_fields = ["Hugo_Symbol", "Chromosome",
                  "Start_Position", "End_Position",
                  "Reference_Allele", "Tumor_Seq_Allele2",
                  "Variant_Classification", "Variant_Type",
                  "Tumor_Sample_Barcode"]

    maf = read_maf(maf_input_file_name, maf_fields)
    write_maf(maf, maf_output_file_name)

main()