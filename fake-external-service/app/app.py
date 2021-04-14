import datetime
import os

from flask import Flask, request, current_app, send_from_directory

from read_write_maf import read_maf, write_maf

app = Flask(__name__)
UPLOAD_FOLDER = './static'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/v1/process', methods=['POST'])
def upload():
    print("hello")
    file = request.files['file']
    return save(file)


def save(file):
    maf_fields = ["Hugo_Symbol", "Chromosome",
                  "Start_Position", "End_Position",
                  "Reference_Allele", "Tumor_Seq_Allele2",
                  "Variant_Classification", "Variant_Type",
                  "Tumor_Sample_Barcode"]

    maf = read_maf(file, maf_fields)
    output_file_name = "GBM_MEMo_filtered-{}-maf".format(datetime.datetime.now().isoformat())

    write_maf(maf, "{}/{}".format(app.config['UPLOAD_FOLDER'], output_file_name))

    return output_file_name


@app.route('/v1/uploads/<path:filename>', methods=['GET'])
def download(filename):
    uploads = os.path.join(current_app.root_path, app.config['UPLOAD_FOLDER'])
    return send_from_directory(directory=uploads, filename=filename, )


if __name__ == '__main__':
    app.run()
