using System.ComponentModel;

namespace ConcertPro.Controller
{
    partial class Dashboard
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.SpectaclesDataGridView = new System.Windows.Forms.DataGridView();
            this.FilterSpectaclesDataGridView = new System.Windows.Forms.DataGridView();
            this.FilterButton = new System.Windows.Forms.Button();
            this.ResetFilterButton = new System.Windows.Forms.Button();
            this.BuyButton = new System.Windows.Forms.Button();
            this.LogoutButton = new System.Windows.Forms.Button();
            this.SpectacleDateInput = new System.Windows.Forms.DateTimePicker();
            this.BuyerNameInput = new System.Windows.Forms.TextBox();
            this.NumberOfTicketsInput = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.SpectaclesDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.FilterSpectaclesDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(161, 30);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(249, 40);
            this.label1.TabIndex = 0;
            this.label1.Text = "LISTA ARTISTI";
            // 
            // SpectaclesDataGridView
            // 
            this.SpectaclesDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.SpectaclesDataGridView.Location = new System.Drawing.Point(12, 73);
            this.SpectaclesDataGridView.Name = "SpectaclesDataGridView";
            this.SpectaclesDataGridView.RowTemplate.Height = 28;
            this.SpectaclesDataGridView.Size = new System.Drawing.Size(640, 312);
            this.SpectaclesDataGridView.TabIndex = 1;
            // 
            // FilterSpectaclesDataGridView
            // 
            this.FilterSpectaclesDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.FilterSpectaclesDataGridView.Location = new System.Drawing.Point(12, 406);
            this.FilterSpectaclesDataGridView.Name = "FilterSpectaclesDataGridView";
            this.FilterSpectaclesDataGridView.RowTemplate.Height = 28;
            this.FilterSpectaclesDataGridView.Size = new System.Drawing.Size(640, 312);
            this.FilterSpectaclesDataGridView.TabIndex = 2;
            this.FilterSpectaclesDataGridView.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.FilterSpectaclesDataGridView_CellContentClick);
            // 
            // FilterButton
            // 
            this.FilterButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FilterButton.Location = new System.Drawing.Point(683, 73);
            this.FilterButton.Name = "FilterButton";
            this.FilterButton.Size = new System.Drawing.Size(136, 43);
            this.FilterButton.TabIndex = 3;
            this.FilterButton.Text = "Filtrare";
            this.FilterButton.UseVisualStyleBackColor = true;
            this.FilterButton.Click += new System.EventHandler(this.FilterButton_Click);
            // 
            // ResetFilterButton
            // 
            this.ResetFilterButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ResetFilterButton.Location = new System.Drawing.Point(841, 73);
            this.ResetFilterButton.Name = "ResetFilterButton";
            this.ResetFilterButton.Size = new System.Drawing.Size(215, 43);
            this.ResetFilterButton.TabIndex = 4;
            this.ResetFilterButton.Text = "Resetare Filtrare";
            this.ResetFilterButton.UseVisualStyleBackColor = true;
            this.ResetFilterButton.Click += new System.EventHandler(this.ResetFilterButton_Click);
            // 
            // BuyButton
            // 
            this.BuyButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.BuyButton.Location = new System.Drawing.Point(1077, 73);
            this.BuyButton.Name = "BuyButton";
            this.BuyButton.Size = new System.Drawing.Size(145, 43);
            this.BuyButton.TabIndex = 5;
            this.BuyButton.Text = "Cumparare";
            this.BuyButton.UseVisualStyleBackColor = true;
            this.BuyButton.Click += new System.EventHandler(this.BuyButton_Click);
            // 
            // LogoutButton
            // 
            this.LogoutButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.LogoutButton.Location = new System.Drawing.Point(1099, 664);
            this.LogoutButton.Name = "LogoutButton";
            this.LogoutButton.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.LogoutButton.Size = new System.Drawing.Size(123, 43);
            this.LogoutButton.TabIndex = 6;
            this.LogoutButton.Text = "Logout";
            this.LogoutButton.UseVisualStyleBackColor = true;
            this.LogoutButton.Click += new System.EventHandler(this.LogoutButton_Click);
            // 
            // SpectacleDateInput
            // 
            this.SpectacleDateInput.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.SpectacleDateInput.Location = new System.Drawing.Point(904, 155);
            this.SpectacleDateInput.Name = "SpectacleDateInput";
            this.SpectacleDateInput.Size = new System.Drawing.Size(318, 30);
            this.SpectacleDateInput.TabIndex = 7;
            // 
            // BuyerNameInput
            // 
            this.BuyerNameInput.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.BuyerNameInput.Location = new System.Drawing.Point(904, 212);
            this.BuyerNameInput.Name = "BuyerNameInput";
            this.BuyerNameInput.Size = new System.Drawing.Size(318, 35);
            this.BuyerNameInput.TabIndex = 8;
            // 
            // NumberOfTicketsInput
            // 
            this.NumberOfTicketsInput.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.NumberOfTicketsInput.Location = new System.Drawing.Point(904, 279);
            this.NumberOfTicketsInput.Name = "NumberOfTicketsInput";
            this.NumberOfTicketsInput.Size = new System.Drawing.Size(318, 35);
            this.NumberOfTicketsInput.TabIndex = 9;
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(683, 157);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(180, 35);
            this.label2.TabIndex = 10;
            this.label2.Text = "Data Spectacol";
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(683, 212);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(215, 35);
            this.label3.TabIndex = 11;
            this.label3.Text = "Nume Cumparator";
            // 
            // label4
            // 
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(683, 279);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(158, 35);
            this.label4.TabIndex = 12;
            this.label4.Text = "Numar Bilete";
            // 
            // Dashboard
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1250, 730);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.NumberOfTicketsInput);
            this.Controls.Add(this.BuyerNameInput);
            this.Controls.Add(this.SpectacleDateInput);
            this.Controls.Add(this.LogoutButton);
            this.Controls.Add(this.BuyButton);
            this.Controls.Add(this.ResetFilterButton);
            this.Controls.Add(this.FilterButton);
            this.Controls.Add(this.FilterSpectaclesDataGridView);
            this.Controls.Add(this.SpectaclesDataGridView);
            this.Controls.Add(this.label1);
            this.Name = "Dashboard";
            this.Text = "Dashboard";
            this.Load += new System.EventHandler(this.Dashboard_Load);
            ((System.ComponentModel.ISupportInitialize)(this.SpectaclesDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.FilterSpectaclesDataGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;

        private System.Windows.Forms.DateTimePicker SpectacleDateInput;
        private System.Windows.Forms.TextBox BuyerNameInput;
        private System.Windows.Forms.TextBox NumberOfTicketsInput;
        private System.Windows.Forms.Label label2;

        private System.Windows.Forms.Button FilterButton;
        private System.Windows.Forms.Button ResetFilterButton;
        private System.Windows.Forms.Button BuyButton;
        private System.Windows.Forms.Button LogoutButton;

        private System.Windows.Forms.DataGridView FilterSpectaclesDataGridView;

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DataGridView SpectaclesDataGridView;

        #endregion
    }
}