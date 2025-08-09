import pandas as pd
from pathlib import Path
import logging
from datetime import datetime

PROJECT_ROOT = Path(__file__).parent.parent

def setup_logging():
    log_dir = PROJECT_ROOT / "logs"
    log_dir.mkdir(exist_ok=True)
    
    log_file = log_dir / f"csv_processor_{datetime.now().strftime('%Y%m%d_%H%M%S')}.log"
    
    logging.basicConfig(
        level=logging.INFO,
        format='%(asctime)s - %(levelname)s - %(message)s',
        handlers=[
            logging.FileHandler(log_file),
            logging.StreamHandler()
        ],
        force=True
    )
    
    return logging.getLogger(__name__)

def load_and_combine_data():
    logger = logging.getLogger(__name__)

    logger.info("Starting data loading and combining...")

    csv_files = list(PROJECT_ROOT.glob("data/*.csv"))
    logger.info(f"Found {len(csv_files)} CSV files")

    if not csv_files:
        raise FileNotFoundError("No CSV files found in the data directory")
    
    dataframes = []
    for file in csv_files:
        df = pd.read_csv(file)
        dataframes.append(df)

    combined_df = pd.concat(dataframes)
    logger.info(f"Combined data count: {len(combined_df)} rows")

    logger.info("Data loading and combining completed successfully!")
    return combined_df

def clean_data(df):
    logger = logging.getLogger(__name__)
    logger.info("Starting data cleaning process...")
    
    original_count = len(df)
    logger.info(f"Original data: {original_count} rows")
    
    df_cleaned = df.copy()
    df_cleaned['date'] = pd.to_datetime(df_cleaned['date'], errors='coerce')
    logger.info(f"Date column converted to datetime")
    

    df_cleaned = df_cleaned.dropna(subset=["transaction_id", "date", "customer_id"])
    logger.info(f"After removing missing values: {len(df_cleaned)} rows")
    
    df_cleaned = df_cleaned.sort_values(by="date", ascending=False)
    df_cleaned = df_cleaned.drop_duplicates(subset=["transaction_id"], keep="first")
    logger.info(f"After removing duplicates: {len(df_cleaned)} rows")
    
    df_cleaned = df_cleaned.sort_values("transaction_id", ascending=True)
    
    removed_count = original_count - len(df_cleaned)
    logger.info(f"Data cleaning completed. Removed {removed_count} rows")
    
    df_cleaned.to_csv("output/cleaned_data.csv", index=False)
    logger.info(f"Cleaned data saved to cleaned_data.csv")
    return df_cleaned

def calculate_total_sales_per_branch(df):
    logger = logging.getLogger(__name__)

    logger.info("Starting total sales per branch calculation...")

    df["total_sales"] = df["price"] * df["quantity"]


    total_sales_per_branch = df.groupby("branch")["total_sales"] .sum().reset_index()
    
    total_sales_per_branch.columns = ["branch", "total"]

    total_sales_per_branch.to_csv("output/total_sales_per_branch.csv", index=False)

    logger.info(f"Total sales per branch rows: {len(total_sales_per_branch)}")
    logger.info(f"Total sales saved to total_sales_per_branch.csv")
    logger.info("Total sales per branch calculation completed successfully!")

    return total_sales_per_branch

def main():
    
    logger = setup_logging()
    logger.info("Starting the CSV processor")

    try:
        # Combine data
        combined_df = load_and_combine_data()

        # Clean data
        cleaned_df = clean_data(combined_df)

        # Calculate total sales per branch
        total_sales_per_branch = calculate_total_sales_per_branch(cleaned_df)
        logger.info("CSV processing completed successfully!")

    except Exception as e:
        logger.error(f"An error occurred: {e}")
        raise

if __name__ == "__main__":
    main()